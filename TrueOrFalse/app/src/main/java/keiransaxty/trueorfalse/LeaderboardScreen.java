package keiransaxty.trueorfalse;

/*
   Created by Keiran Saxty (2015)
   GitHub: https://github.com/KeiranSaxty
*/

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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import io.paperdb.Paper;

public class LeaderboardScreen extends AppCompatActivity {

    private List<HighScoreObject> highScoresList;
    private ListView highScoresListView;
    private Button resetScoresBtn;
    private TextView emptyMessageLbl;
    private int btnID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_screen);

        //Retrieve values passed from last activity
        Bundle extras = getIntent().getExtras();
        btnID = extras.getInt("btnID");

        //Initialise PaperDB
        Paper.init(this);

        //Assign layout elements to objects
        highScoresListView = (ListView) findViewById(R.id.highScoresListView);
        resetScoresBtn = (Button)findViewById(R.id.resetScoresBtn);
        emptyMessageLbl = (TextView)findViewById(R.id.emptyMessageLbl);

        highScoresList = Paper.book().read("highscores", new ArrayList<HighScoreObject>());

        SortList(highScoresList); //Iterate through list and sort scores
        //Create new instance of adapter class
        HighScoreAdapter adapter = new HighScoreAdapter(highScoresList);
        //Set adapter to list view elements
        highScoresListView.setAdapter(adapter);

        //Listen for 'Clear' button-click
        resetScoresBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highScoresList = Paper.book().read("highscores", new ArrayList<HighScoreObject>());
                //Clear PaperDB book of highscores
                Paper.book().delete("highscores");
                //Refresh screen/ activity layout
                setContentView(R.layout.activity_leaderboard_screen);
            }
        });

        //If there are high-scores in the list
        if(highScoresList.size() > 0) {
            //Make the "empty leaderboard" message invisible
            emptyMessageLbl.setVisibility(View.INVISIBLE);
        }
        //Else if there are none
        else
        {
            //Make the "empty leaderboard" message visible
            emptyMessageLbl.setVisibility(View.VISIBLE);
        }
    }

    //Adapter handles layout of rows in the listview element
    private class HighScoreAdapter extends ArrayAdapter<HighScoreObject> {

        public HighScoreAdapter(List<HighScoreObject> items) {
            super(LeaderboardScreen.this, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //If convertView returns a null value
            if (convertView == null) {
                //Inflate 'row_highscores.xml' layout elements in listview
                convertView = getLayoutInflater().inflate(R.layout.activity_row_highscore, null);
            }

            //Retrieves and formats values from highscore object
            TextView titleLbl = (TextView) convertView.findViewById(R.id.titleLbl);//Assign row textview element to new instance
            HighScoreObject highscore = highScoresList.get(position);//Get highscore object for this row
            Date date = new Date(highscore.GetTimeStamp()); //Create new instance of date
            SimpleDateFormat dateFmt = new SimpleDateFormat("dd-MM-yyyy"); //Create new instance of simpleDateFormat

            //Output highscore object values to row
            titleLbl.setText("Difficulty: " + highscore.GetDifficulty() + " - "
                             + "Score: " + highscore.GetPlayerScore() + " - "
                             + "Player: " + highscore.GetPlayerName() + " - "
                             + "Date: " + dateFmt.format(date));

            return convertView;
        }
    }

    private void SortList(List highScoresList) {
        //If there is more than one object in the list
        if(highScoresList.size() > 1)
        {
            //Standard-library Android sort function
            Collections.sort(highScoresList, new Comparator<HighScoreObject>() {
                public int compare(HighScoreObject a, HighScoreObject b) {
                    if (a.GetPlayerScore() < b.GetPlayerScore()) {
                        return 1;
                    } else if (a.GetPlayerScore() > b.GetPlayerScore()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        }
    }

    //Listen for physical key press
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //If 'back button' is pressed on device
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            //If this screen was accessed by the 'Submit' button
            if (btnID == 0) {
                finish(); //Close activity
                startActivity(new Intent(LeaderboardScreen.this, MenuScreen.class)); //Start 'Menu Screen' activity
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
