package com.v4ivstudio.cocan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseUser;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Parse.initialize(this, "Y1BfDVrSpsOFfCqhesLXhfKYyYFyw1cm3kt0SgYj", "vYvn95xmRgOky76HPh69JaHgGA32K9AnVpaL7kEL");
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    if (currentUser != null) {
                        loginSuccessful();
                    } else {
                        Intent loginActivity = new Intent("com.v4ivstudio.cocan.LOGINACTIVITY");
                        startActivity(loginActivity);
                    }
                }
            }
        };
        timer.start();

    }
    private void loginSuccessful()
    {
        Intent wallActivity = new Intent("com.v4ivstudio.cocan.WALLACTIVITY");
        startActivity(wallActivity);
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
