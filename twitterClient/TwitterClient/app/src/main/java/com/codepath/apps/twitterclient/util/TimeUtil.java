package com.codepath.apps.twitterclient.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by manoj on 9/29/17.
 */

public class TimeUtil {

    public static long getTimeInMills(String time) throws ParseException {
        SimpleDateFormat sdf;
        Date d = null;
        try {
            sdf = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy");
            d = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.getTimeInMillis();
    }
}
