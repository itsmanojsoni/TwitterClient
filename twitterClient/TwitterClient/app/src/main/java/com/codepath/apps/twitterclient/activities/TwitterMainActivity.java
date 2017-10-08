package com.codepath.apps.twitterclient.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.util.Network;

import butterknife.ButterKnife;

import static com.codepath.apps.twitterclient.util.Network.isNetworkAvailable;


public class TwitterMainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.bnLogin);

        if (!Network.isNetworkAvailable()) {
            Toast.makeText(this,"Network Connection Error",Toast.LENGTH_LONG).show();
        }

        // Shared Pref to store share token and token String
        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(this);

        String token = mSettings.getString("token",null);
        String tokenSecret = mSettings.getString("tokenSecret",null);

        if (token == null || tokenSecret == null) {

            button.setOnClickListener(view -> {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            });
        } else {
            Intent intent = new Intent(getApplicationContext(), TimeLineActivity.class);
            startActivity(intent);
        }
    }
}
