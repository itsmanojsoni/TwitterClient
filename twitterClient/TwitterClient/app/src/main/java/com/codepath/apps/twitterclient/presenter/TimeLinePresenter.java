package com.codepath.apps.twitterclient.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.twitterclient.application.TwitterApplication;
import com.codepath.apps.twitterclient.models.TwitterResponse;
import com.codepath.apps.twitterclient.models.User;
import com.codepath.apps.twitterclient.util.Network;

import java.util.ArrayList;
import java.util.List;


import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.codepath.apps.twitterclient.util.Network.isNetworkAvailable;


/**
 * Created by manoj on 9/28/17.
 */

public class TimeLinePresenter {

    private Context context;
    private static final String TAG = "TimeLinePresenter";

    private int count = 20;
    private Long sinceId = 1L;
    private Long maxId = -1L;

    public interface OnLoadTwitterFeedListener {
        void onLoadTwitterFeed(List<TwitterResponse> response);

    }


    public interface OnLoadUserInfo {
        void onLoadUserInfo (User response);
    }





    public TimeLinePresenter(Context context) {
        this.context = context;
    }

    public void loadHomeTwitterFeed(OnLoadTwitterFeedListener listener) {
        TwitterApplication.getRestClient()
                .getHomeTimeline(count, sinceId, maxId)
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
                        if (twitterResponses.size() > 0) {
                            maxId = twitterResponses.get(twitterResponses.size() - 1).getId();
                            listener.onLoadTwitterFeed(twitterResponses);
                        }
                    }
                });
    }

    public void loadMentionTwitterFeed(OnLoadTwitterFeedListener listener) {
        TwitterApplication.getRestClient()
                .getMentionTimeline(count, sinceId, maxId)
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
                        if (twitterResponses.size() > 0) {
                            maxId = twitterResponses.get(twitterResponses.size() - 1).getId();
                            listener.onLoadTwitterFeed(twitterResponses);
                        }
                    }
                });
    }

    public void loadUserTimeLineFeed(String screenName, OnLoadTwitterFeedListener listener) {
        TwitterApplication.getRestClient()
                .getUserTimeline(count, sinceId, maxId, screenName)
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
                        if (twitterResponses.size() > 0) {
                            maxId = twitterResponses.get(twitterResponses.size() - 1).getId();
                            listener.onLoadTwitterFeed(twitterResponses);
                        }
                    }
                });
    }

    public void loadUserInfo(OnLoadUserInfo listener) {
        TwitterApplication.getRestClient()
                .getUserInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<User>() {
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
                    public void onNext(User twitterResponses) {
                        listener.onLoadUserInfo(twitterResponses);
                    }
                });
    }

    public void resetMaxId() {
        this.maxId = -1L;
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
                        checkError();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(TwitterResponse twitterResponse) {
                        List<TwitterResponse> twitterResponseList = new ArrayList<TwitterResponse>();
                        twitterResponseList.add(twitterResponse);
                        listener.onLoadTwitterFeed(twitterResponseList);
                    }
                });
    }


    private void checkError() {

        if (!Network.isNetworkAvailable()) {
            Toast.makeText(TwitterApplication.getContext(),"Network Connection Error",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Network Connection Error", Toast.LENGTH_LONG).show();
        }
    }


}
