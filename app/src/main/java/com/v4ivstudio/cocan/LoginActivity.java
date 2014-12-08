package com.v4ivstudio.cocan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginActivity extends Activity {
    protected EditText usrnm;
    protected EditText pswd;
    protected Button signin;
    protected Button signup;
    private String username;
    private String password;
    protected TextView erroruorp;
    protected TextView forgotpswd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Parse.initialize(this, "Y1BfDVrSpsOFfCqhesLXhfKYyYFyw1cm3kt0SgYj", "vYvn95xmRgOky76HPh69JaHgGA32K9AnVpaL7kEL");
        usrnm = (EditText) findViewById(R.id.userName);
        pswd = (EditText) findViewById(R.id.password);
        erroruorp = (TextView) findViewById(R.id.erroruorp);
        signin = (Button) findViewById(R.id.loginButton);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = usrnm.getText().toString();
                password = pswd.getText().toString();

                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (e == null && user != null) {
                            loginSuccessful();
                            finish();
                        } else if (user == null) {
                            usernameOrPasswordIsInvalid();
                        } else {
                            somethingWentWrong();
                        }
                    }
                });
            }
        });
        forgotpswd = (TextView) findViewById(R.id.forgotpswd);
        forgotpswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotPassword = new Intent("com.v4ivstudio.cocan.FORGOTPASSWORDACTIVITY");
                startActivity(forgotPassword);
            }
        });
        signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupActivity = new Intent("com.v4ivstudio.cocan.SIGNUPACTIVITY");
                startActivity(signupActivity);
            }
        });
    }


    private void loginSuccessful()
    {
        Intent wallActivity = new Intent("com.v4ivstudio.cocan.WALLACTIVITY");
        startActivity(wallActivity);
        Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_LONG).show();
    }
    public void usernameOrPasswordIsInvalid(){
        erroruorp.setText("Username or Password is invalid!");
    }
    public void somethingWentWrong(){
        //erroruorp.setText("Oops! Something went wrong, please try later.");
        Toast.makeText(LoginActivity.this, "Oops! Something went wrong, try again later.", Toast.LENGTH_LONG).show();
    }


}
