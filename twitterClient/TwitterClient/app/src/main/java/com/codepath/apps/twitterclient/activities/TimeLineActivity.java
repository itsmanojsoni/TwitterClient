package com.codepath.apps.twitterclient.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.adapter.TwitterFeedAdapter;
import com.codepath.apps.twitterclient.fragment.ComposeDialogueFragment;
import com.codepath.apps.twitterclient.models.TwitterResponse;
import com.codepath.apps.twitterclient.presenter.TimeLinePresenter;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.offset;

public class TimeLineActivity extends AppCompatActivity implements ComposeDialogueFragment.ComposeTweetDialogListener {

    private static final String TAG = "TimeLineActivity";
    private List<TwitterResponse> twitterResponses = new ArrayList<>();

    private TwitterFeedAdapter twitterFeedAdapter;
    private TimeLinePresenter timeLinePresenter;

    @BindView(R.id.rvTwitterClient)
    RecyclerView recyclerView;
    @BindView(R.id.fbCompose)
    FloatingActionButton compose;

    private Handler handler = new Handler();
    private static final int TIME_OUT = 250;

    private SwipeRefreshLayout swipeContainer;
    private EndlessRecyclerViewScrollListener scrollListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        timeLinePresenter = new TimeLinePresenter(this);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        twitterFeedAdapter = new TwitterFeedAdapter(getApplicationContext(), twitterResponses,
                position -> {
                    Intent intent = new Intent(getApplicationContext(), TwitterDetailActivity.class);
                    TwitterResponse twitterResponse = twitterResponses.get(position);
                    intent.putExtra("twitter", Parcels.wrap(twitterResponse));
                    startActivity(intent);

                });

        recyclerView.setAdapter(twitterFeedAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadMoreData();
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        compose.setOnClickListener(view -> {
            ComposeDialogueFragment.newInstance("Compose");
            FragmentManager fm = TimeLineActivity.this.getSupportFragmentManager();
            ComposeDialogueFragment composeDialogFragment = ComposeDialogueFragment.newInstance("Compose");
            composeDialogFragment.show(fm, "fragment_edit_name");

        });


        swipeContainer = findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(() -> {
            fetchTimelineAsync();
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    @Override
    public void onResume() {
        super.onResume();
        // Define the code block to be executed
        Runnable runnableCode = () -> loadMoreData();

        handler.postDelayed(runnableCode, TIME_OUT);

    }

    private void loadMoreData() {
        Log.d(TAG, "load More Data offset = " + offset);
        timeLinePresenter.loadTwitterFeed(twitterResponse -> {
            twitterResponses.addAll(twitterResponse);
            recyclerView.post(() -> {
                twitterFeedAdapter.notifyDataSetChanged();
            });
            return;
        });
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        timeLinePresenter.postTwitterStatus(inputText, response -> {
            twitterResponses.add(0, response.get(0));
            twitterFeedAdapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(0);
        });
    }


    private void fetchTimelineAsync() {
        clear();
        loadMoreData();
    }

    public void clear() {
        twitterResponses.clear();
        twitterFeedAdapter.notifyDataSetChanged();
        scrollListener.resetState();
        swipeContainer.setRefreshing(false);
        recyclerView.scrollToPosition(0);
        timeLinePresenter.resetMaxId();
    }

}
