package keiransaxty.trueorfalse;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class QuestionScreen extends AppCompatActivity {

    private Button trueBtn;
    private Button falseBtn;
    private TextView questionLbl;
    private TextView scoreCounterLbl;
    private ImageView questionImage;
    private int questionIndex;
    private QuestionObject currentQuestion;
    private int currentScore;

    private List<QuestionObject> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_screen);

        questionIndex = 0;
        currentScore = 0;

        //Assign interface elements to variables
        trueBtn = (Button)findViewById(R.id.trueBtn);
        falseBtn = (Button)findViewById(R.id.falseBtn);
        questionLbl = (TextView)findViewById(R.id.questionLbl);
        questionImage = (ImageView)findViewById(R.id.questionImage);
        scoreCounterLbl = (TextView)findViewById(R.id.scoreCounterLbl);
        scoreCounterLbl.setText("Score: " + currentScore);


        //Listen for button click
        trueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckAnswer(true);
            }
        });

        //Listen for button click
        falseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckAnswer(false);
            }
        });

        GenerateQuestions();

        SetUpQuestions();


    }

    private void GenerateQuestions()
    {
        questions = new ArrayList<>();

        questions.add(new QuestionObject("Rio De Janeiro is the capital of Brazil", false, R.drawable.brazil));
        questions.add(new QuestionObject("Canberra is the capital of Australia.", true, R.drawable.australia));
        questions.add(new QuestionObject("Amsterdamn is the capital of the Netherlands.",true, R.drawable.netherlands));
        questions.add(new QuestionObject("Shanghai is the capital of China.", false, R.drawable.china));
        questions.add(new QuestionObject("St. Petersbug is the capital of Russia.", false, R.drawable.russia));
        questions.add(new QuestionObject("Helsinki is the capital of Finland.", true, R.drawable.finland));
        questions.add(new QuestionObject("Prague is the capital of Czech Republic", true, R.drawable.czech));
        questions.add(new QuestionObject("Kaunas is the capital of Lithuania.", false, R.drawable.lithuania));


        //Toast.makeText(QuestionScreen.this, questions.size(), Toast.LENGTH_SHORT).show();

    }

    private void SetUpQuestions()
    {
        if(questionIndex == questions.size())
        {
            //End of questions reached
           EndGame();
        }
        else
        {
            currentQuestion = questions.get(questionIndex);

            questionLbl.setText(currentQuestion.getQuestionText());
            questionImage.setImageResource(currentQuestion.getImage());


            questionIndex++;

        }
    }

    private void CheckAnswer(boolean answer)
    {
        if(answer == currentQuestion.getAnswer())
        {
            Toast.makeText(QuestionScreen.this, "Correct!", Toast.LENGTH_SHORT).show();
            currentScore ++;
        }
        else
        {
            Toast.makeText(QuestionScreen.this, "Inorrect!", Toast.LENGTH_SHORT).show();
        }

        scoreCounterLbl.setText("Score: " + currentScore);
        SetUpQuestions();
    }

    private void EndGame()
    {
        finish();
        startActivity(new Intent(QuestionScreen.this, EndGameScreen.class)
        .putExtra("playerScore", currentScore).putExtra("questionCount", questions.size()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            new AlertDialog.Builder(this)
                    .setTitle("Return to menu")
                    .setMessage("Are you sure you want to return to the menu? Your score will not be saved.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //return to menu
                            finish();
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


}
