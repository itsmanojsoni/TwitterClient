package com.codepath.apps.twitterclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class TimeLineActivity extends AppCompatActivity {

    private TwitterClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_home_feed);

        Toast.makeText(this,"Twitter Home Feed Activity",Toast.LENGTH_LONG).show();

        client   =   TwitterApplication.getRestClient();
        populateTimeLine();
        Button button = findViewById(R.id.button);

        button.setOnClickListener(view -> Toast.makeText(getApplicationContext(),"Button Clicked",Toast.LENGTH_LONG).show());
    }

    private void populateTimeLine() {

        // Rx JAVA code her to get the response
        // call back should come here




    }
}
