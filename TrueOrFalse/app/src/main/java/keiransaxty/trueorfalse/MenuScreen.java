package keiransaxty.trueorfalse;

/*
   Created by Keiran Saxty (2015)
   GitHub: https://github.com/KeiranSaxty
*/

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.parse.Parse;

public class MenuScreen extends AppCompatActivity {

    private Button playGameBtn;
    private Button scoreScreenBtn;
    private Button aboutBtn;
    private Button exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);

        //Assign layout elements to objects
        playGameBtn = (Button)findViewById(R.id.playGameBtn);
        scoreScreenBtn = (Button)findViewById(R.id.scoreScreenBtn);
        aboutBtn = (Button)findViewById(R.id.aboutBtn);
        exitBtn = (Button)findViewById(R.id.exitBtn);

        //If network connection is not currently active
        if(!isOnline()) {
            //Output warning
            Toast.makeText(MenuScreen.this, "You must be connected to the internet to play this game", Toast.LENGTH_SHORT).show();
        }

        //Listen for 'Play Game' button-click
        playGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
                    startActivity(new Intent(MenuScreen.this, GuideScreen.class));
            }
        });

        //Listen for 'Leaderboard' button-click
        scoreScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MenuScreen.this, LeaderboardScreen.class).putExtra("btnID", 0));
            }
        });

        //Listen for 'About' button-click
        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MenuScreen.this, AboutScreen.class));
            }
        });

        //Listen for 'Exit' button-click
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExitApp();
            }
        });
    }

    //Function to create exit alert-dialog message
    private void ExitApp()
    {
        //Create new AlertDialog
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit the app?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //If 'yes' is pressed
                        finish(); //Close activity
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //If 'no' is pressed
                        dialog.dismiss();//Close dialog
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    //Listen for physical key-press
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //If 'back button' is pressed on device
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            //Call function to exit application
            ExitApp();
        }
        return super.onKeyDown(keyCode, event);
    }

    //Checks whether device is connected to network
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
