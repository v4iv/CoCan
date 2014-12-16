package com.v4ivstudio.cocan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;


public class CommentActivity extends ActionBarActivity {

    public String sStatus;
    public String postUser;
    String objectId;
    private ListView commentwall;
    private EditText comments;
    private ImageButton post;
    private String content;
    private String user;
    private TextView statusview;
    private TextView userview;
    private TextView timvi;
    public SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Parse.initialize(this, "Y1BfDVrSpsOFfCqhesLXhfKYyYFyw1cm3kt0SgYj", "vYvn95xmRgOky76HPh69JaHgGA32K9AnVpaL7kEL");
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        commentwall = (ListView) findViewById(R.id.listView2);
        comments = (EditText) findViewById(R.id.editComment);
        post = (ImageButton) findViewById(R.id.postComment);
        Intent intent = getIntent();
        objectId = intent.getStringExtra("objectId");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("StatusUpdate");
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject status, ParseException e) {
                if (e == null) {
                    sStatus = status.getString("Status");
                    statusview = (TextView) findViewById(R.id.updtView);
                    statusview.setText(sStatus);
                    postUser = status.getString("Username");
                    userview = (TextView) findViewById(R.id.usrView);
                    userview.setText(postUser);
                    String tim = status.getCreatedAt().toString();
                    timvi = (TextView) findViewById(R.id.timeView);
                    timvi.setText(tim);

                } else {
                    // something went wrong
                }
            }
        });
        retrieveComments();
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                user = currentUser.getUsername();
                content = comments.getText().toString();
                comments.setText("");
                if (content.isEmpty()) {
                    Toast.makeText(CommentActivity.this, "Comments cannot be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    ParseObject commentPost = new ParseObject("Comments");
                    commentPost.put("Username", user);
                    commentPost.put("Content", content);
                    commentPost.put("parentstatus", objectId);
                    commentPost.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {

                                Toast.makeText(CommentActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                                retrieveComments();
                            } else {
                                e.printStackTrace();
                                Toast.makeText(CommentActivity.this, "Oops! Try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.customColor, R.color.accentColor);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveComments();
                mSwipeRefreshLayout.setRefreshing(true);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            //Toast.makeText(CommentActivity.this, "Refreshing", Toast.LENGTH_SHORT).show();
            retrieveComments();
            mSwipeRefreshLayout.setRefreshing(true);
            return true;
        }
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void retrieveComments() {

        ParseQuery<ParseObject> queryCmmt = new ParseQuery<ParseObject>("Comments");
        queryCmmt.whereEqualTo("parentstatus", objectId);
        queryCmmt.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        queryCmmt.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> commentList, ParseException e) {
                if (e == null) {
                    // commentList now has the comments for myPost
                    List<ParseObject> sComment = commentList;
                    CommentAdapter adapter = new CommentAdapter(commentwall.getContext(), sComment);
                    commentwall.setAdapter(adapter);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                else {
                    //no comments

                }

            }
        });
    }
}
