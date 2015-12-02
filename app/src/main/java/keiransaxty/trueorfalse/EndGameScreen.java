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
        Intent mIntent = getIntent();
        final int finalScore = mIntent.getIntExtra("playerScore", 0);
        final int questionCount = mIntent.getIntExtra("questionCount", 0);

        Paper.init(this);

        (playerNameFld = (EditText)findViewById(R.id.playerNameFld))
        .setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        endMessageLbl = (TextView)findViewById(R.id.endMessageLbl);
        finalScoreLbl = (TextView)findViewById(R.id.finalScoreLbl);
        submitScoreBtn = (Button)findViewById(R.id.submitScoreBtn);
        scoreScreenBtn = (Button)findViewById(R.id.scoreScreenBtn);
        menuScreenBtn = (Button)findViewById(R.id.menuScreenBtn);

        finalScoreLbl.setText("Final Score: " + finalScore + " / " + questionCount);

        submitScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playerNameFld.length() > 0) {
                    SubmitScore(playerNameFld.getText().toString(), finalScore, new Date().getTime());
                    finish();
                    startActivity(new Intent(EndGameScreen.this, LeaderboardScreen.class).putExtra("btnID", 0));
                }
                else
                {
                    Toast.makeText(EndGameScreen.this, "You must enter a name to submit a score", Toast.LENGTH_SHORT).show();
                }
            }
        });

        scoreScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EndGameScreen.this, LeaderboardScreen.class).putExtra("btnID", 1));
            }
        });

        //Listen for button click
        menuScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(EndGameScreen.this, MenuScreen.class));
            }
        });
    }

    private void SubmitScore(final String playerName, final int finalScore, final long timeStamp)
    {
        List<HighScoreObject> writeScoreList = Paper.book().read("highscores", new ArrayList<HighScoreObject>());

        HighScoreObject highScore = new HighScoreObject();
        highScore.SetValues(playerName, finalScore, timeStamp);

        writeScoreList.add(highScore);

        Paper.book().write("highscores", writeScoreList);
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
