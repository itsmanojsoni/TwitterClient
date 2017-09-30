package com.codepath.apps.twitterclient.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.models.TwitterResponse;
import com.codepath.oauth.OAuthBaseClient;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.api.BaseApi;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static com.github.scribejava.core.model.OAuthConstants.CONSUMER_KEY;
import static com.github.scribejava.core.model.OAuthConstants.CONSUMER_SECRET;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/scribejava/scribejava/tree/master/scribejava-apis/src/main/java/com/github/scribejava/apis
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient  {

	// Where to get all the tokens - https://apps.twitter.com/app/14287608/keys

	public static final BaseApi REST_API_INSTANCE = TwitterApi.instance(); // Change this
	public static final String REST_URL = "https://api.twitter.com/"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "J9kMDIZR1EB2tyoLdvuIr2qwS";       // Change this
	public static final String REST_CONSUMER_SECRET = "n2a9PK8bxNwYQhluHRpbWtJ8um3aqcqOt4JAD16y6vykS4v4ZQ"; // Change this

	private static final String ACCESS_TOKEN = "73057146-h1FjE95WWZx4O0CIBHile7T9ays6DZJGNzYMVizbd";
	private static final String TOKEN_SECRET = "iD5zdg8pIfFEWxwerrZAegQnyb6mWDtDmbWTt95hOh9ph";

	// Landing page to indicate the OAuth flow worked in case Chrome for Android 25+ blocks navigation back to the app.
	public static final String FALLBACK_URL = "https://codepath.github.io/android-rest-client-template/success.html";

	// See https://developer.chrome.com/multidevice/android/intents
	public static final String REST_CALLBACK_URL_TEMPLATE = "intent://%s#Intent;action=android.intent.action.VIEW;scheme=%s;package=%s;S.browser_fallback_url=%s;end";
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

		Log.d(TAG, "Shared Preference data token = "+token);
		Log.d(TAG, "Shared Preference data tokenSecret = "+tokenSecret);



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
	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	public Observable<List<TwitterResponse>> getHomeTimeline(Integer count, Integer since_id) {

		return  twitterService.getHomeTimeLine();
	}

	public Observable<TwitterResponse> postStatus(String status) {
		return twitterService.postStatus(status);
	}

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}
