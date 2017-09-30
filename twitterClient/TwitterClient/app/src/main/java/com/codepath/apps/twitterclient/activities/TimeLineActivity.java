package com.codepath.apps.twitterclient.activities;

import android.content.Context;
import android.os.Handler;
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

import static android.R.attr.offset;
import static android.net.sip.SipErrorCode.TIME_OUT;


public class TimeLineActivity extends AppCompatActivity {

    private static final String TAG = "TimeLineActivity";
    private List<TwitterResponse> twitterResponses = new ArrayList<>();

    private TwitterFeedAdapter twitterFeedAdapter;
    private TimeLinePresenter timeLinePresenter;

    @BindView(R.id.rvTwitterClient)
    RecyclerView recyclerView;

    private Handler handler = new Handler();
    private static final  int TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        ButterKnife.bind(this);


        timeLinePresenter = new TimeLinePresenter(this);

        Toast.makeText(this, "Time Line Activity", Toast.LENGTH_LONG).show();

        String test = "Just a test to see if it is working";
//      timeLinePresenter.postTwitterStatus(test);


        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        twitterFeedAdapter = new TwitterFeedAdapter(getApplicationContext(), twitterResponses,
                position -> Log.d(TAG, "Twitter Item Clicked"));

        recyclerView.setAdapter(twitterFeedAdapter);

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.d(TAG, "onLoad More and page is : "+page);
                loadMoreData(page, view);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Define the code block to be executed
        Runnable runnableCode = new Runnable() {
            @Override
            public void run() {
                loadMoreData(0, recyclerView);
            }
        };

        handler.postDelayed(runnableCode,TIME_OUT);
    }

    private void loadMoreData(int offset, RecyclerView view) {
            Log.d(TAG, "load More Data offset = "+offset);
            timeLinePresenter.loadTwitterFeed(twitterResponse -> {
            twitterResponses.addAll(twitterResponse);
            view.post(() -> twitterFeedAdapter.notifyItemRangeInserted(twitterFeedAdapter.getItemCount(),
                    twitterResponses.size() - 1));
                return;
            });
        }
}
