
package com.codepath.apps.twitterclient.models;
public class User {

    String name;
    String id;
    String screen_name;
    String profile_image_url;
    boolean verified;


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