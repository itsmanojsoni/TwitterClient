package com.codepath.apps.twitterclient.activities;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.fragment.TimeLineFragment;
import com.codepath.apps.twitterclient.fragment.UserFragment;
import com.codepath.apps.twitterclient.models.TwitterResponse;
import com.codepath.apps.twitterclient.models.User;
import com.codepath.apps.twitterclient.presenter.TimeLinePresenter;

import org.parceler.Parcels;

import java.util.List;

public class UserProfile extends AppCompatActivity implements TimeLineFragment.OnFragmentInteractionListener{

    private static final String TAG = "UserProfile";

    private ImageView profilPic;
    private TextView followerCount;
    private TextView followingCount;
    private TextView name;

    private TwitterResponse twitterResponse;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profilPic = findViewById(R.id.ivProfilePic);
        followerCount = findViewById(R.id.tvFollowerUserCount);
        followingCount = findViewById(R.id.tvFollowingUserCount);
        name = findViewById(R.id.tvUserName);


        twitterResponse = Parcels.unwrap(getIntent().getParcelableExtra("twitter"));

        user = twitterResponse.getUser();

        UserFragment userFragment = UserFragment.newInstance("Test", user.getScreen_name());
        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.userTimeLineContainer, userFragment);
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();

        // get User Credential to display the name on the tool bar
        TimeLinePresenter timeLinePresenter = new TimeLinePresenter(this);

        timeLinePresenter.loadUserInfo(response -> {
            Log.d(TAG, "onLoadUser Info and User Screen Name is : "+response.getScreen_name());
            getSupportActionBar().setTitle(user.getScreen_name());
            setData();

        });

    }

    @Override
    public void onFragmentInteraction(Object data) {
    }

    private void setData () {

        if (user != null) {
            if (user.getFollowers_count() != null) {
                followerCount.setText(String.valueOf(user.getFollowers_count()) + " Followers");
            }
            name.setText(user.getName());
            if (user.getFollowing_count() != null) {
                followingCount.setText(String.valueOf(user.getFollowing_count()) + " Following");
            }
            Glide.with(this).load(user.getProfile_image_url()).into(profilPic);
        }
    }
}
