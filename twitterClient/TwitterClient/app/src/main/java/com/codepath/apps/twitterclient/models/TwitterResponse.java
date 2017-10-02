package com.codepath.apps.twitterclient.models;

import org.parceler.Parcel;

@Parcel
public class TwitterResponse {

    String text;
    Long retweet_count;
    User user;

    public Long getFavorite_count() {
        return favorite_count;
    }

    Long favorite_count;

    public Entities getEntities() {
        return entities;
    }

    Entities entities;

    Long id;
    String created_at;

    public TwitterResponse () {

    }

    public String getText() {
        return text;
    }

    public Long getRetweet_count() {
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