
package com.codepath.apps.twitterclient.models;

import org.parceler.Parcel;

@Parcel
public class User {
    String name;
    String screen_name;
    String profile_image_url;
    boolean verified;
    Long followers_count;

    public String getDescription() {
        return description;
    }

    public Long getFriends_count() {
        return friends_count;
    }

    String description;

    public Long getFollowing_count() {
        return friends_count;
    }

    Long friends_count;


    public Long getFollowers_count() {
        return followers_count;
    }

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


    public String getScreen_name() {
        return screen_name;
    }
}