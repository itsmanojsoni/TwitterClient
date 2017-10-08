package com.codepath.apps.twitterclient.util;

import android.content.Context;
import android.util.Log;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.application.TwitterApplication;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

/**
 * Created by manoj on 9/29/17.
 */

public class StringUtil {

    public static String formatShortHumanTimestamp(long timestamp) {
        long duration = System.currentTimeMillis() - timestamp;

        if (duration < 0) {
            Log.w("FG/Strings", "Timestamp cannot be in the future!");
            duration = 1000;
        }

        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        long hours = TimeUnit.MILLISECONDS.toHours(duration);
        long days = TimeUnit.MILLISECONDS.toDays(duration);

        Context context = TwitterApplication.getContext();
        if (seconds < 60) {
            return context.getString(R.string.twitter_string_second_abbreviation, seconds);
        } else if (minutes < 60) {
            return context.getString(R.string.twitter_string_minute_abbreviation, minutes);
        } else if (hours < 24) {
            return context.getString(R.string.twitter_string_hour_abbreviation, hours);
        } else if (days < 7) {
            return context.getString(R.string.twitter_string_day_abbreviation, days);
        } else {
            return context.getString(R.string.twitter_string_week_abbreviation, days / 7);
        }
    }

    public static String truncateNumber(long value, int decimalPlaces) {
        if (value == 0) {
            return String.valueOf(value);
        }
        StringBuilder sb = new StringBuilder("###");
        if (decimalPlaces > 0) {
            sb.append(".");
        }
        for (int i = 0; i < decimalPlaces; i++) {
            sb.append("#");
        }
        DecimalFormat decimalFormat = new DecimalFormat(sb.toString());
        String[] suffix = {"", "K", "M", "B", "T", "Quad", "Quin"};
        int power = 0;
        while(Math.pow(10, power) <= value) {
            power+=3;
        }
        power-=3;
        String formattedNumber = decimalFormat.format(value / Math.pow(10, power));
        if (formattedNumber.length() > 4) { // Limit to 3 digits
            formattedNumber = formattedNumber.substring(0, 4);
            if (formattedNumber.charAt(3) == '.') { // If the last digit is '.' remove it
                formattedNumber = formattedNumber.replace(".","");
            }
        }
        return formattedNumber + suffix[power/3];
    }


}
