package com.codepath.apps.twitterclient.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codepath.apps.twitterclient.R;

import butterknife.ButterKnife;


public class TwitterMainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.bnLogin);

        button.setOnClickListener(view -> {

            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        });


    }
}
