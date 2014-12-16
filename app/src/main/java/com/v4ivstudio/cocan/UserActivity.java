package com.v4ivstudio.cocan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class UserActivity extends ActionBarActivity {

    public String user;
    public String nm;
    public TextView username;
    public TextView name;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    public ListView myPost;
    public List<ParseObject> sPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Parse.initialize(this, "Y1BfDVrSpsOFfCqhesLXhfKYyYFyw1cm3kt0SgYj", "vYvn95xmRgOky76HPh69JaHgGA32K9AnVpaL7kEL");
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myPost = (ListView) findViewById(R.id.usrPosts);
        ParseUser currentUser = ParseUser.getCurrentUser();
        user = currentUser.getUsername();
        username = (TextView) findViewById(R.id.userN);
        username.setText(user);
        nm = currentUser.get("name").toString();
        name = (TextView) findViewById(R.id.naMe);
        name.setText(nm);
        retrievePosts();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.customColor, R.color.accentColor);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrievePosts();
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }
    public void retrievePosts() {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("StatusUpdate");
        query.whereEqualTo("Username",user);
        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> posts, ParseException e) {
                if (e == null) {
                    sPosts = posts;
                    PostAdapter adapter = new PostAdapter(myPost.getContext(), sPosts);
                    myPost.setAdapter(adapter);
                    mSwipeRefreshLayout.setRefreshing(false);

                } else {
                    //something went wrong
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
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
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
