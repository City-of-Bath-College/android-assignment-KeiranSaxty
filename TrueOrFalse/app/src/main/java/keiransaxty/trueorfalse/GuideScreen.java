package keiransaxty.trueorfalse;

/*
   Created by Keiran Saxty (2015)
   GitHub: https://github.com/KeiranSaxty
*/

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GuideScreen extends AppCompatActivity {

    private Button playBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_screen);

        playBtn = (Button)findViewById(R.id.playBtn);

        //Listen for 'Play Game' button-click
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(GuideScreen.this, DifficultyScreen.class));
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //If 'back button' is pressed on device
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            //Close activity
            finish();
            startActivity(new Intent(GuideScreen.this, MenuScreen.class));
        }
        return super.onKeyDown(keyCode, event);
    }

}
