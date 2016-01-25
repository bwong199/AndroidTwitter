package com.parse.starter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewFeed extends AppCompatActivity {

    ListView listView;

    SimpleAdapter simpleAdapter;

    List<Map<String, String>> tweetData = new ArrayList<Map<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feed);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.yourFeedListView);

        simpleAdapter = new SimpleAdapter(this, tweetData, android.R.layout.simple_list_item_2, new String[]{"content", "username"}, new int[]{android.R.id.text1, android.R.id.text2});



        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tweet");
        query.whereContainedIn("username", ParseUser.getCurrentUser().getList("isFollowing"));
        query.orderByDescending("createdAt");
        query.setLimit(20);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> tweetObjects, ParseException e) {
                if (e == null) {
                    if (tweetObjects.size() > 0) {
                        for (ParseObject tweetObject : tweetObjects) {
                            Map<String, String> tweet = new HashMap<String, String>(2);
                            tweet.put("content", tweetObject.getString("content"));
                            tweet.put("username", tweetObject.getString("username"));

                            tweetData.add(tweet);
                            System.out.println(tweetObject.get("username") + " " +  tweetObject.get("content"));
                        }
//
                        listView.setAdapter(simpleAdapter);
                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }


        });


//        for(int i = 1; i < 5; i++){
//            Map<String, String> tweet = new HashMap<String, String>(2);
//            tweet.put("content", "Tweet content " + String.valueOf(i));
//            tweet.put("username", "Twitter User " + String.valueOf(i));
//
//            tweetData.add(tweet);
//
//
//
//
//        }


    }

}
