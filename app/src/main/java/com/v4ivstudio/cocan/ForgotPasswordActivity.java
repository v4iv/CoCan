package com.v4ivstudio.cocan;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;


public class ForgotPasswordActivity extends ActionBarActivity {

    public Toolbar toolbar;
    protected EditText fpemail;
    protected Button fpchange;
    protected TextView message;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Parse.initialize(this, "Y1BfDVrSpsOFfCqhesLXhfKYyYFyw1cm3kt0SgYj", "vYvn95xmRgOky76HPh69JaHgGA32K9AnVpaL7kEL");
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        fpemail = (EditText) findViewById(R.id.fpemail);
        fpchange = (Button) findViewById(R.id.fpchange);
        message = (TextView) findViewById(R.id.message);
        fpchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = fpemail.getText().toString();
                ParseUser.requestPasswordResetInBackground(email,
                        new RequestPasswordResetCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    //message.setText("An email was successfully sent with reset instructions.");
                                    Toast.makeText(ForgotPasswordActivity.this, "An email was successfully sent with reset instructions.", Toast.LENGTH_LONG).show();
                                } else {
                                    //message.setText("Oops! Something went wrong, try again later.");
                                    Toast.makeText(ForgotPasswordActivity.this, "Oops! Something went wrong, try again later.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

}
