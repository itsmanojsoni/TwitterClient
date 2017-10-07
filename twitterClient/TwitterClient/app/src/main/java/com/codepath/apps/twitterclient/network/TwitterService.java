package com.codepath.apps.twitterclient.network;

import com.codepath.apps.twitterclient.models.TwitterResponse;
import com.codepath.apps.twitterclient.models.User;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface TwitterService {

    @GET("1.1/statuses/home_timeline.json")
    Observable<List<TwitterResponse>> getHomeTimeLineMax(@Query("count") int count, @Query("max_id") Long maxId);

    @GET("1.1/statuses/home_timeline.json")
    Observable<List<TwitterResponse>> getHomeTimeLineSince(@Query("count") int count,@Query("since_id") Long sinceId);


    @GET("1.1/statuses/mentions_timeline.json")
    Observable<List<TwitterResponse>> getMentionTimeLineMax(@Query("count") int count, @Query("max_id") Long maxId);

    @GET("1.1/statuses/mentions_timeline.json")
    Observable<List<TwitterResponse>> getMentionTimeLineSince(@Query("count") int count,@Query("since_id") Long sinceId);


    @GET("1.1/statuses/user_timeline.json")
    Observable<List<TwitterResponse>> getUserTimeLineMax(@Query("count") int count,
                                                         @Query("max_id") Long maxId,
                                                         @Query("screen_name") String screenName);

    @GET("1.1/statuses/user_timeline.json")
    Observable<List<TwitterResponse>> getUserTimeLineSince(@Query("count") int count,
                                                           @Query("since_id") Long sinceId,
                                                           @Query("screen_name") String screenName);

        @GET("1.1/account/verify_credentials.json")
    Observable<User> getUserInfo();


    @FormUrlEncoded
    @POST("1.1/statuses/update.json")
    Observable<TwitterResponse> postStatus(@Field("status") String status);
}