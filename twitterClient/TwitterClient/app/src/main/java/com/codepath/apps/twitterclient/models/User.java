
package com.codepath.apps.twitterclient.models;

import org.parceler.Parcel;

@Parcel
public class User {
    String name;
    String id;
    String screen_name;
    String profile_image_url;
    boolean verified;

    public Long getFavourites_count() {
        return favourites_count;
    }

    Long favourites_count;

    public User() {

    }

    public String getName() {
        return name;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public boolean isVerified() {
        return verified;
    }

    public String getId() {
        return id;
    }

    public String getScreen_name() {
        return screen_name;
    }
}