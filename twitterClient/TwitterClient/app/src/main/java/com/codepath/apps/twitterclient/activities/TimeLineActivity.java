package com.codepath.apps.twitterclient.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.adapter.SampleFragmentPagerAdapter;
import com.codepath.apps.twitterclient.adapter.TwitterFeedAdapter;
import com.codepath.apps.twitterclient.fragment.ComposeDialogueFragment;
import com.codepath.apps.twitterclient.fragment.HomeFeedFragment;
import com.codepath.apps.twitterclient.fragment.MentionFragment;
import com.codepath.apps.twitterclient.fragment.TimeLineFragment;
import com.codepath.apps.twitterclient.models.TwitterResponse;
import com.codepath.apps.twitterclient.models.User;
import com.codepath.apps.twitterclient.presenter.TimeLinePresenter;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.offset;
import static android.net.sip.SipErrorCode.TIME_OUT;
import static com.codepath.apps.twitterclient.R.id.swipeContainer;

public class TimeLineActivity extends AppCompatActivity implements TimeLineFragment.OnFragmentInteractionListener {

    private static final String TAG = "TimeLineActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        ButterKnife.bind(this);


        FloatingActionButton compose = findViewById(R.id.fbCompose);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        ImageView userProfile = findViewById(R.id.ivUserProfile);

        userProfile.setOnClickListener(view -> {
            // get User Credential to display the name on the tool bar
            TimeLinePresenter timeLinePresenter = new TimeLinePresenter(this);

            timeLinePresenter.loadUserInfo(response -> {
                Log.d(TAG, "onLoadUser Info and User Screen Name is : " + response.getScreen_name());
                Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                intent.putExtra("twitter", Parcels.wrap(response));
                startActivity(intent);

            });
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onFragmentInteraction(Object data) {

    }
}
