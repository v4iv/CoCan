package com.v4ivstudio.cocan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignupActivity extends ActionBarActivity {

    public Toolbar toolbar;
    protected EditText name;
    protected EditText username;
    protected EditText email;
    protected EditText password;
    protected Button signup;
    private String nm;
    private String usr;
    private String em;
    private String pswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Parse.initialize(this, "Y1BfDVrSpsOFfCqhesLXhfKYyYFyw1cm3kt0SgYj", "vYvn95xmRgOky76HPh69JaHgGA32K9AnVpaL7kEL");
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        name = (EditText) findViewById(R.id.NameSU);
        username = (EditText) findViewById(R.id.UsernmSU);
        email = (EditText) findViewById(R.id.emailSU);
        password = (EditText) findViewById(R.id.PasswordSU);
        signup = (Button) findViewById(R.id.signupbutton);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nm = name.getText().toString();
                usr = username.getText().toString().toLowerCase();
                em = email.getText().toString();
                pswd = password.getText().toString();

                ParseUser user = new ParseUser();
                user.put("name", nm);
                user.setUsername(usr);
                user.setPassword(pswd);
                user.setEmail(em);

                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            loginSuccessful();
                            finish();
                        } else {
                            Toast.makeText(SignupActivity.this, "Oops! Something went wrong, Check the details and Try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }

    private void loginSuccessful() {
        Intent wallActivity = new Intent("com.v4ivstudio.cocan.WALLACTIVITY");
        startActivity(wallActivity);
    }
}
