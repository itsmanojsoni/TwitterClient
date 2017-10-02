package com.codepath.apps.twitterclient.models;

import org.parceler.Parcel;

@Parcel
public class TwitterResponse {

    String text;
    Long retweet_count;
    User user;
    Long id;
    String created_at;
    Long favorite_count;

    public Long getFavorite_count() {
        return favorite_count;
    }


    public Entities getEntities() {
        return entities;
    }

    Entities entities;


    public TwitterResponse() {

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