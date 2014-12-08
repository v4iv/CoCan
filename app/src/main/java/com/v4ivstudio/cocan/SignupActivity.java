package com.v4ivstudio.cocan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignupActivity extends Activity {

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
        name = (EditText) findViewById(R.id.NameSU);
        username = (EditText) findViewById(R.id.UsernmSU);
        email = (EditText) findViewById(R.id.emailSU);
        password = (EditText) findViewById(R.id.PasswordSU);
        signup = (Button) findViewById(R.id.signupbutton);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nm=name.getText().toString();
                usr=username.getText().toString();
                em=email.getText().toString();
                pswd=password.getText().toString();

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
                        } else  {
                            Toast.makeText(SignupActivity.this, "Oops! Something went wrong, try again later.", Toast.LENGTH_LONG).show();
                            /*AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                            builder.setMessage("Fields Cannot Be Empty");
                            builder.setTitle("Error");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();*/
                        }
                    }
                });

            }
        });
    }
    private void loginSuccessful()
    {
        Intent wallActivity = new Intent("com.v4ivstudio.cocan.WALLACTIVITY");
        startActivity(wallActivity);
    }
}
