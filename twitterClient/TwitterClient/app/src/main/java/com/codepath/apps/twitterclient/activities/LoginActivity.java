package com.codepath.apps.twitterclient.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.codepath.apps.twitterclient.R;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

import static com.codepath.apps.twitterclient.network.TwitterClient.REST_CONSUMER_KEY;
import static com.codepath.apps.twitterclient.network.TwitterClient.REST_CONSUMER_SECRET;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "TwitterAuthActivity";
    private final static String CALLBACK = "oauth://twitter";

    private WebView mWebView;
    private String mAuthUrl;

    private OAuthProvider mProvider;
    private OAuthConsumer mConsumer;

    private SharedPreferences mSettings;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mWebView = findViewById(R.id.wvTwitter);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.startsWith("oauth")){
                    Uri uri = Uri.parse(url);
                    onOAuthCallback(uri);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        if (TextUtils.isEmpty(mAuthUrl)) {
            mWebView.loadUrl(mAuthUrl);
        }

        mSettings = PreferenceManager.getDefaultSharedPreferences(this);
        editor = mSettings.edit();

//        String token = mSettings.getString("token",null);
//        String tokenSecret = mSettings.getString("tokenSecret",null);
//
//        if (token == null || tokenSecret == null) {
//            initTwitter();
//        } else {
//
//            Intent intent = new Intent(getApplicationContext(), TimeLineActivity.class);
//            startActivity(intent);
//        }

        initTwitter();

    }


    private void initTwitter() {
        mConsumer = new DefaultOAuthConsumer(
                REST_CONSUMER_KEY,
                REST_CONSUMER_SECRET);

        mProvider = new DefaultOAuthProvider(
                "https://api.twitter.com/oauth/request_token",
                "https://api.twitter.com/oauth/access_token",
                "https://api.twitter.com/oauth/authorize");


        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    mAuthUrl = mProvider.retrieveRequestToken(mConsumer, CALLBACK);
                } catch (OAuthMessageSignerException e) {
                    e.printStackTrace();
                } catch (OAuthNotAuthorizedException e) {
                    e.printStackTrace();
                } catch (OAuthExpectationFailedException e) {
                    e.printStackTrace();
                } catch (OAuthCommunicationException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                if (!TextUtils.isEmpty(mAuthUrl)) {
                    mWebView.loadUrl(mAuthUrl);
                }
            }
        }.execute();

    }


    private void onOAuthCallback(final Uri uri) {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {

                String pinCode = uri.getQueryParameter("oauth_verifier");
                try {
                    mProvider.retrieveAccessToken(mConsumer, pinCode);

                    String token = mConsumer.getToken();
                    String tokenSecret = mConsumer.getTokenSecret();

                    editor.putString("token",token);
                    editor.putString("tokenSecret",tokenSecret);
                    editor.apply();

                } catch (OAuthMessageSignerException e) {
                    e.printStackTrace();
                } catch (OAuthNotAuthorizedException e) {
                    e.printStackTrace();
                } catch (OAuthExpectationFailedException e) {
                    e.printStackTrace();
                } catch (OAuthCommunicationException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Intent intent = new Intent(getApplicationContext(), TimeLineActivity.class);
                startActivity(intent);
            }
        }.execute();
    }
}
