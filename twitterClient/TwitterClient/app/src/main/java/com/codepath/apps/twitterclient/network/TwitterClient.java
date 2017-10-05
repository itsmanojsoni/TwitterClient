package com.codepath.apps.twitterclient.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.codepath.apps.twitterclient.models.TwitterResponse;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public class TwitterClient  {

	public static final String REST_URL = "https://api.twitter.com/"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "J9kMDIZR1EB2tyoLdvuIr2qwS";       // Change this
	public static final String REST_CONSUMER_SECRET = "n2a9PK8bxNwYQhluHRpbWtJ8um3aqcqOt4JAD16y6vykS4v4ZQ"; // Change this

	private TwitterService twitterService;
	private  static  TwitterClient twitterClient = null;

	public static TwitterClient getInstance(Context context) {
		if (twitterClient == null) {
			twitterClient = new TwitterClient(context);
		}
		return twitterClient;
	}


	public TwitterClient(Context context) {

		SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(context);

		String token = mSettings.getString("token",null);
		String tokenSecret = mSettings.getString("tokenSecret",null);


		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

		OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(REST_CONSUMER_KEY, REST_CONSUMER_SECRET);
		consumer.setTokenWithSecret(token, tokenSecret);

		OkHttpClient client = new OkHttpClient.Builder()
				.addInterceptor(new SigningInterceptor(consumer))
				.addInterceptor(interceptor)
				.build();

		final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		final Retrofit retrofit = new Retrofit.Builder().baseUrl(REST_URL)
				.client(client)
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create(gson))
				.build();
		twitterService = retrofit.create(TwitterService.class);
	}

	public Observable<List<TwitterResponse>> getHomeTimeline(Integer count, Long sinceId, Long maxId) {
		if (maxId > 0 ) {
			return  twitterService.getHomeTimeLineMax(count, (maxId - 1));
		} else {
			return  twitterService.getHomeTimeLineSince(count,sinceId);
		}
	}

	public Observable<List<TwitterResponse>> getMentionTimeline(Integer count, Long sinceId, Long maxId) {
		if (maxId > 0 ) {
			return  twitterService.getHomeTimeLineMax(count, (maxId - 1));
		} else {
			return  twitterService.getHomeTimeLineSince(count,sinceId);
		}
	}

	public Observable<TwitterResponse> postStatus(String status) {
		return twitterService.postStatus(status);
	}
}
