package com.v4ivstudio.cocan;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends ActionBarActivity {

    private String usernameText;
    private String passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        Button signIn = (Button) findViewById(R.id.signIn);
        Button signUp = (Button) findViewById(R.id.signUp);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameText = username.getText().toString();
                usernameText = usernameText.toLowerCase();
                passwordText = password.getText().toString();

                ParseUser.logInInBackground(usernameText, passwordText, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if (e == null && parseUser != null) {
                            loginSuccessful();
                            finish();
                        } else if (parseUser == null) {
                            usernameOrPasswordIsInvalid();
                        } else {
                            somethingWentWrong();
                        }
                    }
                });
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpActivity = new Intent("com.v4ivstudio.cocan.SIGNUPACTIVITY");
                startActivity(signUpActivity);
            }
        });
    }

    void loginSuccessful() {
        Intent openMainActivity = new Intent("com.v4ivstudio.cocan.MAINACTIVITY");
        startActivity(openMainActivity);
        Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
    }

    void usernameOrPasswordIsInvalid() {
        Toast.makeText(this, "Username or Password is Invalid!", Toast.LENGTH_LONG).show();
    }

    void somethingWentWrong() {
        Toast.makeText(this, "Oops! Something went wrong, try again later.", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reset_password) {
            Intent resetPasswordActivity = new Intent("com.v4ivstudio.cocan.RESETPASSWORDACTIVITY");
            startActivity(resetPasswordActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
