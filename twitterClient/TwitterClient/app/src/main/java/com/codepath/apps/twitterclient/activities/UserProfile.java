package com.codepath.apps.twitterclient.activities;

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
import com.codepath.apps.twitterclient.models.User;
import com.codepath.apps.twitterclient.presenter.TimeLinePresenter;
import com.codepath.apps.twitterclient.util.StringUtil;

import org.parceler.Parcels;

public class UserProfile extends AppCompatActivity implements TimeLineFragment.OnFragmentInteractionListener{

    private static final String TAG = "UserProfile";

    private ImageView profilPic;
    private TextView followerCount;
    private TextView followingCount;
    private TextView name;
    private TextView tagLine;

    private User user;
    private static final int DECIMAL_PLACE = 2;

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
        tagLine = findViewById(R.id.tvTagLine);


        user = Parcels.unwrap(getIntent().getParcelableExtra("twitter"));


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

        timeLinePresenter.loadUserInfo(new TimeLinePresenter.OnLoadUserInfo() {
            @Override
            public void onLoadUserInfo(User response) {
                Log.d(TAG, "onLoadUser Info and User Screen Name is : " + response.getScreen_name());
                UserProfile.this.getSupportActionBar().setTitle(user.getScreen_name());
                UserProfile.this.setData();



            }
        });
    }

    @Override
    public void onFragmentInteraction(Object data) {
    }

    private void setData () {

        if (user != null) {
            if (user.getFollowers_count() != null) {

                String follower_count = StringUtil.truncateNumber(user.getFollowers_count(),DECIMAL_PLACE);
                followerCount.setText(follower_count + " Followers ");
            }
            name.setText(user.getName());
            if (user.getFollowing_count() != null) {
                String following_count = StringUtil.truncateNumber(user.getFollowing_count(),DECIMAL_PLACE);
                followingCount.setText(following_count + " Following");
            }
            Glide.with(this).load(user.getProfile_image_url()).into(profilPic);
            if (user.getDescription() != null) {

                Log.d(TAG, "User Text = "+user.getDescription());
            }
            tagLine.setText(user.getDescription());
        }
    }
}
