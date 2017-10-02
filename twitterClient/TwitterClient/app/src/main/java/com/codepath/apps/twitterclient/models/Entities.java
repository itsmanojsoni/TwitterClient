package com.codepath.apps.twitterclient.models;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by manoj on 10/1/17.
 */
@Parcel
public class Entities {


    public Entities() {


    }

    List<Media> media;

    public List<Media> getMedia() {
        return media;
    }


    @Parcel
    public static class Media {

        String display_url;
        String expanded_url;
        String media_url;
        String media_url_https;

        public Media() {

        }

        public String getDisplay_url() {
            return display_url;
        }

        public String getExpanded_url() {
            return expanded_url;
        }

        public String getMedia_url() {
            return media_url;
        }

        public String getMedia_url_https() {
            return media_url_https;
        }

        public String getType() {
            return type;
        }

        String type;

    }


}
