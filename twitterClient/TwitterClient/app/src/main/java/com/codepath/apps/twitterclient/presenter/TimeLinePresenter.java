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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by manoj on 9/28/17.
 */

public class TimeLinePresenter {


    private TwitterClient client;

//    private static final int count = 25;
//    private static final int sinceId = 1;
    private Context context;
    private static final String TAG = "TimeLinePresenter";

    OnLoadTwitterFeedListener listener;
    private int count = 20;
    private Long sinceId = 1L;
    private Long maxId = -1L;
    private Map<Long,Boolean> myMap = new HashMap<>();

    public interface OnLoadTwitterFeedListener {
         void onLoadTwitterFeed(List<TwitterResponse> response);
    }

    public TimeLinePresenter(Context context) {
        this.context = context;
    }

    public void loadTwitterFeed(OnLoadTwitterFeedListener listener) {
        TwitterApplication.getRestClient()
                .getHomeTimeline(count, sinceId,maxId)
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

                        if (twitterResponses.size() > 0 ) {
                            Log.d(TAG, "Twitter Respnse Size : " + twitterResponses.size());
                        maxId = twitterResponses.get(twitterResponses.size() - 1).getId();
//                            maxId = twitterResponses.get(0).getId();
                            Log.d(TAG, "The max ID is : " + maxId);
//                      sinceId = twitterResponses.get(0).getId();

                            for (int i = 0; i < twitterResponses.size(); i++) {

                                if (!myMap.containsKey(twitterResponses.get(i))) {
                                    myMap.put(twitterResponses.get(i).getId(), true);
                                } else {
                                    Log.d(TAG, "Key Alredy exist");
                                    throw new RuntimeException("Key Exist");
                                }
                            }

                            listener.onLoadTwitterFeed(twitterResponses);
                        }
                    }
                });

    }

    public void postTwitterStatus(String status, OnLoadTwitterFeedListener listener) {

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
                        Log.d(TAG, "In onNext for Post and twitter Response is : " + twitterResponse.getText());
                        List<TwitterResponse> twitterResponseList = new ArrayList<TwitterResponse>();
                        twitterResponseList.add(twitterResponse);
                        listener.onLoadTwitterFeed(twitterResponseList);
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
