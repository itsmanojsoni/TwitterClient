package com.codepath.apps.twitterclient.models;

import org.parceler.Parcel;

@Parcel
public class TwitterResponse {

    String text;
    String retweet_count;
    User user;

    Long id;
    String created_at;

    public TwitterResponse () {

    }

    public String getText() {
        return text;
    }

    public String getRetweet_count() {
        return retweet_count;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return id;
    }

    public String getCreated_at() {
        return created_at;
    }

}