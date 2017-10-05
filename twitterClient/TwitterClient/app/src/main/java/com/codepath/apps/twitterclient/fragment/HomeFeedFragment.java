package com.codepath.apps.twitterclient.fragment;

import android.os.Bundle;
import android.util.Log;

import static android.R.attr.offset;

/**
 * Created by manoj on 10/5/17.
 */

public class HomeFeedFragment extends TimeLineFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private static final String TAG = "HomeFeedFragment";

    public HomeFeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFeedFragment newInstance(String param1, String param2) {
        HomeFeedFragment fragment = new HomeFeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void loadMoreData() {
        Log.d(TAG, "load Home Twitter Feed ");
        timeLinePresenter.loadHomeTwitterFeed(twitterResponse -> {
            twitterResponses.addAll(twitterResponse);
            recyclerView.post(() -> {
                twitterFeedAdapter.notifyDataSetChanged();
            });
            return;
        });
    }
}

