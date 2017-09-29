package com.codepath.apps.twitterclient.network;

import com.codepath.apps.twitterclient.models.SampleModel;
import com.codepath.apps.twitterclient.models.TwitterResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface TwitterService {

    @GET("1.1/statuses/home_timeline.json")
    Observable<List<TwitterResponse>> getHomeTimeLine();

}