package com.codepath.apps.twitterclient.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.activities.EndlessRecyclerViewScrollListener;
import com.codepath.apps.twitterclient.activities.TimeLineActivity;
import com.codepath.apps.twitterclient.activities.TwitterDetailActivity;
import com.codepath.apps.twitterclient.activities.UserProfile;
import com.codepath.apps.twitterclient.adapter.TwitterFeedAdapter;
import com.codepath.apps.twitterclient.models.TwitterResponse;
import com.codepath.apps.twitterclient.presenter.TimeLinePresenter;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.R.attr.offset;
import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TimeLineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TimeLineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public abstract class TimeLineFragment extends Fragment implements ComposeDialogueFragment.ComposeTweetDialogListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    protected OnFragmentInteractionListener mListener;

    protected List<TwitterResponse> twitterResponses = new ArrayList<>();

    protected TwitterFeedAdapter twitterFeedAdapter;
    protected TimeLinePresenter timeLinePresenter;

    RecyclerView recyclerView;
//    @BindView(R.id.fbCompose)
//    FloatingActionButton compose;

    private Handler handler = new Handler();
    private static final int TIME_OUT = 250;

    private SwipeRefreshLayout swipeContainer;
    private EndlessRecyclerViewScrollListener scrollListener;

    public TimeLineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimeLineFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static TimeLineFragment newInstance(String param1, String param2) {
//        TimeLineFragment fragment = new TimeLineFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_time_line, container, false);

        recyclerView  = view.findViewById(R.id.rvTwitterClient);

        timeLinePresenter = new TimeLinePresenter(getContext());

        FloatingActionButton compose = view.findViewById(R.id.fbCompose);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        twitterFeedAdapter = new TwitterFeedAdapter(getContext(), twitterResponses,
                new TwitterFeedAdapter.OnItemClickListener() {
                    @Override
                    public void onProfileClicked(int position) {


                        Intent intent = new Intent(TimeLineFragment.this.getContext(), UserProfile.class);
                        TwitterResponse twitterResponse = twitterResponses.get(position);
                        intent.putExtra("twitter", Parcels.wrap(twitterResponse));
                        TimeLineFragment.this.startActivity(intent);

                    }

                    @Override
                    public void onStatusTextClicked(int position) {

                        Intent intent = new Intent(TimeLineFragment.this.getContext(), TwitterDetailActivity.class);
                        TwitterResponse twitterResponse = twitterResponses.get(position);
                        intent.putExtra("twitter", Parcels.wrap(twitterResponse));
                        TimeLineFragment.this.startActivity(intent);

                    }
                });

        recyclerView.setAdapter(twitterFeedAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadMoreData();


            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        final Fragment fragment = this;
        compose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComposeDialogueFragment.newInstance("Compose");
                FragmentManager fm = TimeLineFragment.this.getActivity().getSupportFragmentManager();
                ComposeDialogueFragment composeDialogFragment = ComposeDialogueFragment.newInstance("Compose");
                composeDialogFragment.setTargetFragment(fragment,300);
                composeDialogFragment.show(fm, "fragment_edit_name");

            }
        });


        swipeContainer = view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(() -> {
            fetchTimelineAsync();
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Define the code block to be executed
        loadMoreData();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

//    private void loadMoreData() {
//        Log.d(TAG, "load More Data offset = " + offset);
//        timeLinePresenter.loadTwitterFeed(twitterResponse -> {
//            twitterResponses.addAll(twitterResponse);
//            recyclerView.post(() -> {
//                twitterFeedAdapter.notifyDataSetChanged();
//            });
//            return;
//        });
//    }

    @Override
    public void onFinishEditDialog(String inputText) {
        timeLinePresenter.postTwitterStatus(inputText, response -> {
            twitterResponses.add(0, response.get(0));
            twitterFeedAdapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(0);
        });
    }

    public abstract void loadMoreData();



    private void fetchTimelineAsync() {
        clear();
        loadMoreData();
    }

    public void clear() {
        twitterResponses.clear();
        twitterFeedAdapter.notifyDataSetChanged();
        scrollListener.resetState();
        swipeContainer.setRefreshing(false);
        recyclerView.scrollToPosition(0);
        timeLinePresenter.resetMaxId();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Object data);
    }
}
