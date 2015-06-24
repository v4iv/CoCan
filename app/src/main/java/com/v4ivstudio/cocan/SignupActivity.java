package com.v4ivstudio.cocan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignupActivity extends ActionBarActivity {

    private String name;
    private String usernameText;
    private String passwordText;
    private String passwordVerifyText;
    private String emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        final EditText fullname = (EditText) findViewById(R.id.name);
        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        final EditText passwordVerify = (EditText) findViewById(R.id.passwordVerify);
        final EditText eMail = (EditText) findViewById(R.id.eMail);
        Button signUpButton = (Button) findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                passwordText = password.getText().toString();
                passwordVerifyText = passwordVerify.getText().toString();
                name = fullname.getText().toString();
                usernameText = username.getText().toString();
                emailText = eMail.getText().toString();

                if (usernameText.equals("") || passwordText.equals("") || passwordVerifyText.equals("") || name.equals("") || emailText.equals("")) {
                    Toast.makeText(SignupActivity.this, "Fields Cannot Be Empty!", Toast.LENGTH_LONG).show();
                } else if (passwordVerifyText.equals(passwordText)) {
                    ParseUser parseUser = new ParseUser();

                    parseUser.put("name", name);
                    parseUser.setUsername(usernameText);
                    parseUser.setPassword(passwordText);
                    parseUser.setEmail(emailText);

                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                signUpSuccessful();
                                finish();
                            } else {
                                Toast.makeText(SignupActivity.this, "Oops! Something went wrong, Check the details and Try again.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(SignupActivity.this, "Password Do Not Match!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    void signUpSuccessful() {
        Intent basicInfoActivity = new Intent("com.v4ivstudio.cocan.BASICINFOACTIVITY");
        startActivity(basicInfoActivity);
        Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
    }

}
