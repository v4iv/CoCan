package com.v4ivstudio.cocan;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;


public class ResetPasswordActivity extends ActionBarActivity {

    private EditText emailInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        emailInput = (EditText) findViewById(R.id.emailInput);
        Button resetPassword = (Button) findViewById(R.id.resetPassword);

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString();
                ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(ResetPasswordActivity.this, "An email was successfully sent with reset instructions.", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(ResetPasswordActivity.this, "Oops! Something went wrong, try again later.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

}
