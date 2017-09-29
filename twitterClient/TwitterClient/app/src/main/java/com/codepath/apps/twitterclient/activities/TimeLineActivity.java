package com.codepath.apps.twitterclient.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.presenter.TimeLinePresenter;


public class TimeLineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);


        TimeLinePresenter timeLinePresenter = new TimeLinePresenter(this);
//        timeLinePresenter.loadTwitterFeed();
        String test = "Just a test to see if it is working";
        timeLinePresenter.postTwitterStatus(test);
    }
}
