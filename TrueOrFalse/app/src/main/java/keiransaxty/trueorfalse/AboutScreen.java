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
import android.widget.TextView;

public class AboutScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_screen);



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //If 'back button' is pressed on device
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            //Close activity
            finish();
            startActivity(new Intent(AboutScreen.this, MenuScreen.class));
        }
        return super.onKeyDown(keyCode, event);
    }

}
