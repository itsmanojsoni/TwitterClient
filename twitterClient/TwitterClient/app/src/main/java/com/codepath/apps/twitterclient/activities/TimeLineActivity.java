package com.codepath.apps.twitterclient.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.adapter.TwitterFeedAdapter;
import com.codepath.apps.twitterclient.models.TwitterResponse;
import com.codepath.apps.twitterclient.presenter.TimeLinePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TimeLineActivity extends AppCompatActivity {

    private static final String TAG = "TimeLineActivity";
    private List<TwitterResponse> twitterResponses = new ArrayList<>();

    private TwitterFeedAdapter twitterFeedAdapter;
    private TimeLinePresenter timeLinePresenter;

    @BindView(R.id.rvTwitterClient) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        ButterKnife.bind(this);


        timeLinePresenter = new TimeLinePresenter(this);

        Toast.makeText(this,"Time Line Activity",Toast.LENGTH_LONG).show();

        String test = "Just a test to see if it is working";
//      timeLinePresenter.postTwitterStatus(test);


        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);


        twitterFeedAdapter = new TwitterFeedAdapter(getApplicationContext(), twitterResponses,
                new TwitterFeedAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Log.d(TAG, "Twitter Item Clicked");
                    }
                });

        recyclerView.setAdapter(twitterFeedAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();


        timeLinePresenter.loadTwitterFeed(twitterResponse -> {

            Log.d(TAG, "Twitter Response Size = "+twitterResponse.size());
            twitterResponses.addAll(twitterResponse);
//            twitterFeedAdapter.notifyItemChanged(twitterFeedAdapter.getItemCount(),twitterResponse.size() - 1);
            twitterFeedAdapter.notifyDataSetChanged();

            return;
        });


    }
}
