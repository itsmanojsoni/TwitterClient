package com.codepath.apps.twitterclient.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.models.TwitterResponse;

import org.parceler.Parcels;

public class TwitterDetailActivity extends AppCompatActivity {

    private static final String TAG = "TwitterDetailActivity";

    private ImageView ivProfileImage;
    private TextView tvScreenName;
    private TextView tvUserName;
    private TextView tvStatus;
    private ImageView ivStatusImage;

    private TwitterResponse twitterResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_detail);

        twitterResponse = Parcels.unwrap(getIntent().getParcelableExtra("twitter"));

        Log.d(TAG, "Twitter Text is : "+twitterResponse.getText());

        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvScreenName = findViewById(R.id.tvScreenName);
        tvUserName = findViewById(R.id.tvUserName);
        tvStatus = findViewById(R.id.tvStatusText);
    }

    @Override
    protected void onResume () {
        super.onResume();

        Glide.with(this).load(twitterResponse.getUser().getProfile_image_url()).into(ivProfileImage);
        tvScreenName.setText(twitterResponse.getUser().getScreen_name());
        tvUserName.setText("@"+ twitterResponse.getUser().getName());
        tvStatus.setText(twitterResponse.getText());
    }
}
