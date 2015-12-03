package keiransaxty.trueorfalse;

/*
   Created by Keiran Saxty (2015)
   GitHub: https://github.com/KeiranSaxty
*/

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DifficultyScreen extends AppCompatActivity {

    public Button easyBtn;
    public Button mediumBtn;
    public Button hardBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_screen);

        //Assign layout elements to objects
        easyBtn = (Button)findViewById(R.id.easyBtn);
        mediumBtn = (Button)findViewById(R.id.mediumBtn);
        hardBtn = (Button)findViewById(R.id.hardBtn);

        //Listen for 'Easy Button' button-click
        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline()) {
                    finish();
                    //Start questions with 'easy' difficulty
                    startActivity(new Intent(DifficultyScreen.this, QuestionScreen.class).putExtra("difficultySetting", "Easy"));
                }else
                {
                    //Output connectivity warning
                    Toast.makeText(DifficultyScreen.this, "You must be connected to the internet to play this game", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Listen for 'Medium Button' button-click
        mediumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline()) {
                    finish();
                    //Start questions with 'medium' difficulty
                    startActivity(new Intent(DifficultyScreen.this, QuestionScreen.class).putExtra("difficultySetting", "Medium"));
                }else
                {
                    //Output connectivity warning
                    Toast.makeText(DifficultyScreen.this, "You must be connected to the internet to play this game", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Listen for 'Hard Button' button-click
        hardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline()) {
                    finish();
                    //Start questions with 'hard' difficulty
                    startActivity(new Intent(DifficultyScreen.this, QuestionScreen.class).putExtra("difficultySetting", "Hard"));
                }else
                {
                    //Output connectivity warning
                    Toast.makeText(DifficultyScreen.this, "You must be connected to the internet to play this game", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Checks whether device is connected to network
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
