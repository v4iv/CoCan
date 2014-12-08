package com.v4ivstudio.cocan;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class ParseActivity extends Activity {
    public String update;
    public String user;
    protected List<ParseObject> sStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse);
        Parse.initialize(this, "Y1BfDVrSpsOFfCqhesLXhfKYyYFyw1cm3kt0SgYj", "vYvn95xmRgOky76HPh69JaHgGA32K9AnVpaL7kEL");

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("StatusUpdate");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> status, ParseException e) {
                if(e==null){
                }else {

                }
            }
        });
    }

}
