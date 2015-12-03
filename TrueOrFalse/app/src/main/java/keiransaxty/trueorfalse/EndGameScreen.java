package keiransaxty.trueorfalse;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.paperdb.Paper;


public class EndGameScreen extends AppCompatActivity {

    private EditText playerNameFld;
    private Button submitScoreBtn;
    private Button scoreScreenBtn;
    private Button menuScreenBtn;
    private TextView endMessageLbl;
    private TextView finalScoreLbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game_screen);

        //Retrieve values passed from previous activity
        Bundle extras = getIntent().getExtras();
        final int finalScore = extras.getInt("playerScore");
        final int questionCount = extras.getInt("questionCount");
        final String difficultySetting = extras.getString("difficultySetting");

        //Initialise PaperDB
        Paper.init(this);

        //Assign layout elements to objects
        (playerNameFld = (EditText)findViewById(R.id.playerNameFld))
        .setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS); //Set input type to 'Text' and auto-capitalise
        endMessageLbl = (TextView)findViewById(R.id.endMessageLbl);
        finalScoreLbl = (TextView)findViewById(R.id.finalScoreLbl);
        submitScoreBtn = (Button)findViewById(R.id.submitScoreBtn);
        scoreScreenBtn = (Button)findViewById(R.id.scoreScreenBtn);
        menuScreenBtn = (Button)findViewById(R.id.menuScreenBtn);

        //Initialise final score counter
        finalScoreLbl.setText("Final Score: " + finalScore + " / " + questionCount);

        //Listen for button click
        submitScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playerNameFld.length() > 0) {
                    //Submit values to PaperDB
                    SubmitScore(playerNameFld.getText().toString(), finalScore, new Date().getTime(), difficultySetting);
                    finish();
                    startActivity(new Intent(EndGameScreen.this, LeaderboardScreen.class).putExtra("btnID", 0));
                }
                else
                {
                    //Output lack of name error message
                    Toast.makeText(EndGameScreen.this, "You must enter a name to submit a score", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Listen for button click
        scoreScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to leaderboard screen
                startActivity(new Intent(EndGameScreen.this, LeaderboardScreen.class).putExtra("btnID", 1));
            }
        });

        //Listen for button click
        menuScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Close activity
                finish();
                //Go to leaderboard screen
                startActivity(new Intent(EndGameScreen.this, MenuScreen.class));
            }
        });
    }

    private void SubmitScore(final String playerName, final int finalScore, final long timeStamp, final String difficultySetting)
    {
        //Read PaperDB book into list
        List<HighScoreObject> writeScoreList = Paper.book().read("highscores", new ArrayList<HighScoreObject>());

        //Create new instance of HighScoreObject
        HighScoreObject highScore = new HighScoreObject();
        //Assign values to properties of that instance
        highScore.SetValues(playerName, finalScore, timeStamp, difficultySetting);
        //Add HighScoreObject instance to list
        writeScoreList.add(highScore);

        //Write contents of finalised list back into PaperDB book
        Paper.book().write("highscores", writeScoreList);
    }

    //Listens for physical key press
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //If device 'back button' is pressed
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            new AlertDialog.Builder(this)
                    .setTitle("Return to menu")
                    .setMessage("Are you sure you want to return to the menu? Your score will not be saved.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //return to menu
                            finish();
                            startActivity(new Intent(EndGameScreen.this, MenuScreen.class));
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
}
