package com.codepath.apps.twitterclient.activities;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.fragment.TimeLineFragment;
import com.codepath.apps.twitterclient.fragment.UserFragment;
import com.codepath.apps.twitterclient.models.TwitterResponse;
import com.codepath.apps.twitterclient.models.User;
import com.codepath.apps.twitterclient.presenter.TimeLinePresenter;
import java.util.List;

public class UserProfile extends AppCompatActivity implements TimeLineFragment.OnFragmentInteractionListener{

    private static final String TAG = "UserProfile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        UserFragment userFragment = UserFragment.newInstance("Test", "Test");
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
            getSupportActionBar().setTitle(response.getScreen_name());
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {


    }
}
