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
        Bundle extras =  getIntent().getExtras(); //get extras from previous intent
        btnID = extras.getInt("btnID"); //get value of button pressed to access this screen
        Paper.init(this);

        //Assign layout elements to objects
        highScoresListView = (ListView) findViewById(R.id.highScoresListView);
        resetScoresBtn = (Button)findViewById(R.id.resetScoresBtn);
        emptyMessageLbl = (TextView)findViewById(R.id.emptyMessageLbl);
        highScoresList = Paper.book().read("highscores", new ArrayList<HighScoreObject>());

        SortList(highScoresList);

        HighScoreAdapter adapter = new HighScoreAdapter(highScoresList);
        highScoresListView.setAdapter(adapter);

        resetScoresBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().delete("highscores");
                setContentView(R.layout.activity_leaderboard_screen);
            }
        });

        if(highScoresList.size() > 0) {
            emptyMessageLbl.setVisibility(View.INVISIBLE);
        }
        else
        {
            emptyMessageLbl.setVisibility(View.VISIBLE);
        }
    }

    //Adapter handles rows in the listview object
    private class HighScoreAdapter extends ArrayAdapter<HighScoreObject> {

        public HighScoreAdapter(List<HighScoreObject> items) {
            super(LeaderboardScreen.this, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.activity_row_highscore, null);
            }

            TextView titleLbl = (TextView) convertView.findViewById(R.id.titleLbl);

            HighScoreObject highscore = highScoresList.get(position);

            Date date = new Date(highscore.GetTimeStamp());
            SimpleDateFormat dateFmt = new SimpleDateFormat("dd-MM-yyyy");

            titleLbl.setText("Score: " + highscore.GetPlayerScore() + " - "
                    + "Player: " + highscore.GetPlayerName() + " - "
                    + "Date: " + dateFmt.format(date));

            return convertView;
        }
    }

    private void SortList(List highScoresList) {
        if(highScoresList.size() > 1)
        {
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (btnID == 0) {
                //return to menu
                finish();
                startActivity(new Intent(LeaderboardScreen.this, MenuScreen.class));
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
