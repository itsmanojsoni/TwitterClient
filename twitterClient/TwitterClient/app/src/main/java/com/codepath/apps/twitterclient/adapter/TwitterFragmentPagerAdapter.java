package com.codepath.apps.twitterclient.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.codepath.apps.twitterclient.fragment.HomeFeedFragment;
import com.codepath.apps.twitterclient.fragment.MentionFragment;

/**
 * Created by manoj on 10/5/17.
 */

public class TwitterFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Home", "@Mention" };
    private Context context;

    private static final String TAG = "SimpleFragmentPage";

    public TwitterFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "Position is : "+position);
        switch (position) {
            case 0 :
                return HomeFeedFragment.newInstance("Test", "Work");
            case 1:
                return MentionFragment.newInstance("Test", "Work");
            default:
                return HomeFeedFragment.newInstance("Test", "Work");
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}