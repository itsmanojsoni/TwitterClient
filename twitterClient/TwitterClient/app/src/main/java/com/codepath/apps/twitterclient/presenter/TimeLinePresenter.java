package com.codepath.apps.twitterclient.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.twitterclient.application.TwitterApplication;
import com.codepath.apps.twitterclient.models.TwitterResponse;
import com.codepath.apps.twitterclient.models.User;
import com.codepath.apps.twitterclient.network.TwitterClient;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by manoj on 9/28/17.
 */

public class TimeLinePresenter {


    private TwitterClient client;

    private static final int count = 25;
    private static final int sinceId = 1;
    private Context context;
    private static final String TAG = "TimeLinePresenter";

    OnLoadTwitterFeedListener listener;

    public interface OnLoadTwitterFeedListener {
         void onLoadTwitterFeed(List<TwitterResponse> response);
    }

    public TimeLinePresenter(Context context) {
        this.context = context;
    }

    public void loadTwitterFeed(OnLoadTwitterFeedListener listener) {
        Log.d(TAG, "load Twitter Feed");
        TwitterApplication.getRestClient()
                .getHomeTimeline(count, sinceId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<TwitterResponse>>() {
                    @Override
                    public void onCompleted() {

                        Log.d(TAG, "onComplete");

                    }

                    @Override
                    public void onError(Throwable e) {

                        e.printStackTrace();
                        Log.d(TAG, "Error in loading Feed");

                        checkError();
                    }

                    @Override
                    public void onNext(List<TwitterResponse> twitterResponses) {

                        Log.d("TwitterPresenter", " Twitter Respnse is : " + twitterResponses.toString());

                        Log.d(TAG, " Twitter Text = " + twitterResponses.get(0).getText());
                        // Get all the data here just to be sure we can parse everything
                        User user = twitterResponses.get(0).getUser();

                        Log.d(TAG, "Screen Name = " + user.getScreen_name());
                        Log.d(TAG, "Name = " + user.getName());
                        Log.d(TAG, "profile Image Url" + user.getProfile_image_url());

                        listener.onLoadTwitterFeed(twitterResponses);

                    }
                });

    }

    public void postTwitterStatus(String status) {

        TwitterApplication.getRestClient()
                .postStatus(status)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<TwitterResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(TwitterResponse twitterResponse) {
                        Log.d(TAG, "In onNext and twitter Response is : " + twitterResponse.getText());
                    }
                });
    }


    private void checkError() {

        if (!isNetworkAvailable()) {
            Toast.makeText(context, "Network Connection Error", Toast.LENGTH_LONG).show();
        }
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
