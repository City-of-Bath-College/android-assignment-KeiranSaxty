package keiransaxty.trueorfalse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.parse.Parse;


public class ParseInitialiser extends AppCompatActivity {
    //NOTE: Was having issues with "Parse.initialise()" being called twice
    //      when restarting activities; this custom class was created to remedy this.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialise ParseDB
        Parse.initialize(this, "1oiiGlM1S7WO9IYgUY1LPNbYuXwEJwgrzXFZrM8K", "4CEc7p0sOdgFbHxztFtzMGFtwAHECSuPAxtBUGTp");

        //Close activity so that it cannot be reopened
        finish();

        //Start 'MenuScreen' activity
        startActivity(new Intent(ParseInitialiser.this, MenuScreen.class));
    }
}
