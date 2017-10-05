package com.codepath.apps.twitterclient.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.twitterclient.R;

import static android.R.attr.offset;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MentionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MentionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MentionFragment extends TimeLineFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private static final String TAG = "HomeFeedFragment";

    public MentionFragment() {
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
    public static MentionFragment newInstance(String param1, String param2) {
        MentionFragment fragment = new MentionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void loadMoreData() {
        Log.d(TAG, "Load Mention Twitter Feed = ");
        timeLinePresenter.loadMentionTwitterFeed(twitterResponse -> {
            twitterResponses.addAll(twitterResponse);
            recyclerView.post(() -> {
                twitterFeedAdapter.notifyDataSetChanged();
            });
            return;
        });
    }
}
