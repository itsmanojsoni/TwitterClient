package com.codepath.apps.twitterclient.activities;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.models.Entities;
import com.codepath.apps.twitterclient.models.TwitterResponse;

import org.parceler.Parcels;

import java.util.List;

public class TwitterDetailActivity extends AppCompatActivity {

    private static final String TAG = "TwitterDetailActivity";

    private ImageView ivProfileImage;
    private TextView tvScreenName;
    private TextView tvUserName;
    private TextView tvStatus;
    private ImageView ivMedia;

    private VideoView videoView;

    private TwitterResponse twitterResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_detail);

        twitterResponse = Parcels.unwrap(getIntent().getParcelableExtra("twitter"));

        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvScreenName = findViewById(R.id.tvScreenName);
        tvUserName = findViewById(R.id.tvUserName);
        tvStatus = findViewById(R.id.tvStatusText);
        ivMedia = findViewById(R.id.ivMedia);
        videoView = findViewById(R.id.videoView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Glide.with(this).load(twitterResponse.getUser().getProfile_image_url()).into(ivProfileImage);
        tvScreenName.setText(twitterResponse.getUser().getScreen_name());
        tvUserName.setText("@" + twitterResponse.getUser().getName());
        tvStatus.setText(twitterResponse.getText());

        String urlToDisplay = getUrlToDisplayMedia();

        if (urlToDisplay != null) {
            if ((urlToDisplay.endsWith(".jpeg")) || (urlToDisplay.endsWith(".jpg"))) {
                ivMedia.setVisibility(View.VISIBLE);
                Glide.with(this).load(urlToDisplay).into(ivMedia);
            } else if (urlToDisplay.endsWith(".mp4")) {
                playVideo(urlToDisplay);
            }
        }
    }

    private void playVideo(String url) {
        videoView.setVisibility(View.VISIBLE);
        Uri video = Uri.parse(url);
        videoView.setVideoURI(video);
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            videoView.start();
        });
    }

    private String getUrlToDisplayMedia() {

        List<Entities.Media> media = twitterResponse.getEntities().getMedia();
        if (media != null && media.size() > 0) {
            return twitterResponse.getEntities().getMedia().get(0).getMedia_url_https();
        }
        return null;
    }
}
