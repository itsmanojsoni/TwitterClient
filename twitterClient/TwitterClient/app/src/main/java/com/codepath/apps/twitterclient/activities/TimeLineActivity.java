package com.codepath.apps.twitterclient.activities;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.adapter.TwitterFeedAdapter;
import com.codepath.apps.twitterclient.fragment.ComposeDialogueFragment;
import com.codepath.apps.twitterclient.models.TwitterResponse;
import com.codepath.apps.twitterclient.presenter.TimeLinePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.offset;
import static android.net.sip.SipErrorCode.TIME_OUT;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        ButterKnife.bind(this);

        timeLinePresenter = new TimeLinePresenter(this);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        twitterFeedAdapter = new TwitterFeedAdapter(getApplicationContext(), twitterResponses,
                position -> Log.d(TAG, "Twitter Item Clicked"));

        recyclerView.setAdapter(twitterFeedAdapter);

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
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
    }

    @Override
    public void onResume() {
        super.onResume();
        // Define the code block to be executed
        Runnable runnableCode = new Runnable() {
            @Override
            public void run() {
                loadMoreData();
            }
        };

        handler.postDelayed(runnableCode,TIME_OUT);

    }

    private void loadMoreData() {
        Log.d(TAG, "load More Data offset = " + offset);
        timeLinePresenter.loadTwitterFeed(twitterResponse -> {
            twitterResponses.addAll(twitterResponse);
            recyclerView.post(() -> {
//                    twitterFeedAdapter.notifyItemRangeInserted(twitterFeedAdapter.getItemCount(),
//                            twitterResponses.size() - 1);
                twitterFeedAdapter.notifyDataSetChanged();
            });
            return;
        });
    }

    @Override
    public void onFinishEditDialog(String inputText) {

        Log.d(TAG, "The input Text is : " + inputText);
        timeLinePresenter.postTwitterStatus(inputText, response -> {
            twitterResponses.add(0,response.get(0));
            twitterFeedAdapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(0);
        });
    }
}
