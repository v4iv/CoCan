package com.v4ivstudio.cocan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.parse.Parse;
import com.parse.ParseUser;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        Parse.initialize(this, "Y1BfDVrSpsOFfCqhesLXhfKYyYFyw1cm3kt0SgYj", "vYvn95xmRgOky76HPh69JaHgGA32K9AnVpaL7kEL");     //Parse Init
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    if (currentUser != null) {
                        Intent mainActivity = new Intent("com.v4ivstudio.cocan.MAINACTIVITY");
                        startActivity(mainActivity);
                    } else {
                        Intent loginActivity = new Intent("com.v4ivstudio.cocan.LOGINACTIVITY");
                        startActivity(loginActivity);
                    }
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
