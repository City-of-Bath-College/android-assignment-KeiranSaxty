package keiransaxty.trueorfalse;

/*
   Created by Keiran Saxty (2015)
   GitHub: https://github.com/KeiranSaxty
*/

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.paperdb.Paper;


public class QuestionScreen extends AppCompatActivity {

    private Button trueBtn;
    private Button falseBtn;
    private TextView questionLbl;
    private TextView scoreCounterLbl;
    private ImageView questionImage;
    private int questionIndex;
    private int currentScore;
    private String questionText;
    private boolean questionAnswer;
    private String questionImageName;
    private String difficultySetting;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_screen);

        //Retrieve values passed from previous activity
        Bundle extras = getIntent().getExtras();
        difficultySetting = extras.getString("difficultySetting");

        //Initialise variables
        questionIndex = 0;
        currentScore = 0;

        //Assign interface elements to variables
        (trueBtn = (Button)findViewById(R.id.trueBtn)).setVisibility(View.INVISIBLE);
        (falseBtn = (Button)findViewById(R.id.falseBtn)).setVisibility(View.INVISIBLE);
        (questionLbl = (TextView)findViewById(R.id.questionLbl)).setVisibility(View.INVISIBLE);
        (questionImage = (ImageView)findViewById(R.id.questionImage)).setVisibility(View.INVISIBLE);
        (scoreCounterLbl = (TextView)findViewById(R.id.scoreCounterLbl)).setVisibility(View.INVISIBLE);
        (progressBar = (ProgressBar)findViewById(R.id.progressBar)).setVisibility(View.VISIBLE);

        //Initialise score counter
        scoreCounterLbl.setText("Score: " + currentScore);

        //Listen for button click
        trueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If device has internet connection
                if(isOnline()) {
                    CheckAnswer(true);
                }
                else{
                    Toast.makeText(QuestionScreen.this, "You must be connected to the internet to continue.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Listen for button click
        falseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If device has internet connection
                if (isOnline()) {
                    CheckAnswer(false);
                } else {
                    Toast.makeText(QuestionScreen.this, "You must be connected to the internet to continue.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Call function to pull questions from ParseDB and output to screen
        GetQuestion();
    }

    //Pulls question data from either ParseDB or local storage depending on network connection state
    private void GetQuestion() {
        //Create new query object
        ParseQuery<ParseObject> query = ParseQuery.getQuery(difficultySetting.toString());
        //Set query parameters
        query.whereEqualTo("isUsed", true);
        //Submit query
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> object, com.parse.ParseException e) {
                //If connection to database successful
                if (e == null) {
                    if (questionIndex == object.size()) {
                        EndGame();
                    } else {
                        questionLbl.setText(object.get(questionIndex).getString("questionText"));
                        questionAnswer = object.get(questionIndex).getBoolean("questionAnswer");

                        //NOTE: Couldn't successfully pull images from Parse, so loaded images locally using filename pulled from Parse
                        int resID = getResources().getIdentifier(object.get(questionIndex).getString("country"), "drawable", getPackageName());
                        questionImage.setImageResource(resID);

                        //Make elements invisible temporarily
                        questionImage.setVisibility(View.VISIBLE);
                        questionLbl.setVisibility(View.VISIBLE);
                        trueBtn.setVisibility(View.VISIBLE);
                        falseBtn.setVisibility(View.VISIBLE);
                        scoreCounterLbl.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);

                        //Increment question index
                        questionIndex++;
                    }

                } else {
                    Log.d("Tag", "There was a problem retrieving the data. Check connection and database integrity.");
                }
            }
        });

    }

    //Compares answer given to expected answer and awards points
    private void CheckAnswer(boolean answer) {

        questionImage.setVisibility(View.INVISIBLE);
        questionLbl.setVisibility(View.INVISIBLE);
        trueBtn.setVisibility(View.INVISIBLE);
        falseBtn.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        if (answer == questionAnswer) {
            //Output 'correct' message
            Toast.makeText(QuestionScreen.this, "Correct!", Toast.LENGTH_SHORT).show();
            //Increment player score
            currentScore++;
        } else {
            //Output 'incorrect' message
            Toast.makeText(QuestionScreen.this, "Incorrect!", Toast.LENGTH_SHORT).show();
        }
        //Update score counter
        scoreCounterLbl.setText("Score: " + currentScore);

        //Call function to retrieve next question
        GetQuestion();
    }

    //Closes the activity and starts next activity
    private void EndGame() {
        //Close activity
        finish();
        //start new activity and pass final score/ maximum possible score to it
        startActivity(new Intent(QuestionScreen.this, EndGameScreen.class)
                .putExtra("playerScore", currentScore).putExtra("questionCount", questionIndex).putExtra("difficultySetting", difficultySetting));
    }

    //Listen for physical keypress
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //If 'back button' is pressed on device
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            //Create new AlertDialog
            new AlertDialog.Builder(this)
                    .setTitle("Return to menu")
                    .setMessage("Are you sure you want to return to the menu? Your score will not be saved.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //Close activity
                            finish();
                            //Start 'MenuScreen' activity
                            startActivity(new Intent(QuestionScreen.this, MenuScreen.class));
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //close dialog
                            dialog.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        return super.onKeyDown(keyCode, event);
    }

    //Checks if the application/device currently has network connectivity
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //Return connection state
        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
