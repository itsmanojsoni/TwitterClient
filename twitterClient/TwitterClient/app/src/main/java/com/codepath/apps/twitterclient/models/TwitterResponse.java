package com.codepath.apps.twitterclient.models;

public class TwitterResponse {

    public String getText() {
        return text;
    }

    public String getRetweet_count() {
        return retweet_count;
    }

    public User getUser() {
        return user;
    }

    String text;
    String retweet_count;
    User user;

    public Long getId() {
        return id;
    }

    public String getCreated_at() {
        return created_at;
    }

    Long id;
    String created_at;

}