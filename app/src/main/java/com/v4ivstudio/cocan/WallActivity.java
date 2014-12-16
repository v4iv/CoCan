package com.v4ivstudio.cocan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;


public class WallActivity extends ActionBarActivity {

    public String update;
    public String user;
    public Toolbar toolbar;
    protected EditText status;
    protected ImageButton post;
    protected List<ParseObject> sStatus;
    protected ListView wall;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    public float ele = (float) 4.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);
        Parse.initialize(this, "Y1BfDVrSpsOFfCqhesLXhfKYyYFyw1cm3kt0SgYj", "vYvn95xmRgOky76HPh69JaHgGA32K9AnVpaL7kEL");
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(ele);
        retrieveStatus();
        wall = (ListView) findViewById(R.id.listView);

        status = (EditText) findViewById(R.id.editStatus);
        post = (ImageButton) findViewById(R.id.postStatus);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                user = currentUser.getUsername();
                update = status.getText().toString();
                status.setText("");
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
                                retrieveStatus();
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
        wall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseObject positionUpdate = sStatus.get(position);
                String objectId = positionUpdate.getObjectId();
                Intent commentActivity = new Intent("com.v4ivstudio.cocan.COMMENTACTIVITY");
                commentActivity.putExtra("objectId", objectId);
                startActivity(commentActivity);
            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.customColor, R.color.accentColor);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveStatus();
                mSwipeRefreshLayout.setRefreshing(true);
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
        if (id == R.id.action_refresh) {
            //Toast.makeText(WallActivity.this, "Refreshing", Toast.LENGTH_SHORT).show();
            retrieveStatus();
            mSwipeRefreshLayout.setRefreshing(true);
            return true;
        }
        if (id == R.id.action_user) {
            Intent userActivity = new Intent("com.v4ivstudio.cocan.USERACTIVITY");
            startActivity(userActivity);
            return true;
        }
        if (id == R.id.action_settings) {
            Intent settingsActivity = new Intent("com.v4ivstudio.cocan.SETTINGSACTIVITY");
            startActivity(settingsActivity);
            return true;
        }
        if (id == R.id.action_logout) {
            ParseUser.logOut();
            Intent loginActivity = new Intent("com.v4ivstudio.cocan.LOGINACTIVITY");
            startActivity(loginActivity);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void retrieveStatus() {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("StatusUpdate");
        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> status, ParseException e) {
                if (e == null) {
                    sStatus = status;

                    UpdateAdapter adapter = new UpdateAdapter(wall.getContext(), sStatus);
                    wall.setAdapter(adapter);
                    mSwipeRefreshLayout.setRefreshing(false);

                } else {
                    //something went wrong
                }
            }
        });

    }
}
