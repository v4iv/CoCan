package com.v4ivstudio.cocan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;


public class WallActivity extends Activity {

    protected EditText status;
    protected ImageButton post;
    public String update;
    public String user;

    protected List<ParseObject> sStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);
        Parse.initialize(this, "Y1BfDVrSpsOFfCqhesLXhfKYyYFyw1cm3kt0SgYj", "vYvn95xmRgOky76HPh69JaHgGA32K9AnVpaL7kEL");

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("StatusUpdate");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> status, ParseException e) {
                if(e==null){
                    sStatus = status;

                }else {

                }
            }
        });

        status = (EditText) findViewById(R.id.editStatus);
        post = (ImageButton) findViewById(R.id.postStatus);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                user = currentUser.getUsername();
                update = status.getText().toString();
                if (update.isEmpty()) {
                    Toast.makeText(WallActivity.this, "Status cannot be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    ParseObject statusUpdate = new ParseObject("StatusUpdate");
                    statusUpdate.put("Username", user);
                    statusUpdate.put("Status", update);
                    statusUpdate.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(WallActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                            } else {
                                e.printStackTrace();
                                Toast.makeText(WallActivity.this, "Oops! Try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wall, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            ParseUser.logOut();
            Intent loginActivity = new Intent("com.v4ivstudio.cocan.LOGINACTIVITY");
            startActivity(loginActivity);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
