package com.codepath.apps.twitterclient.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.twitterclient.R;

import butterknife.BindView;

import static android.R.attr.width;

/**
 * Created by manoj on 9/30/17.
 */

public class ComposeDialogueFragment extends DialogFragment implements TextView.OnEditorActionListener {

    private EditText mEditText;
    // 1. Defines the listener interface with a method passing back data result.
    public interface ComposeTweetDialogListener {
        void onFinishEditDialog(String inputText);
    }


    public ComposeDialogueFragment() {
            // Empty constructor is required for DialogFragment
            // Make sure not to add arguments to the constructor
            // Use `newInstance` instead as shown below
        }

        public static ComposeDialogueFragment newInstance(String title) {
            ComposeDialogueFragment frag = new ComposeDialogueFragment();
            Bundle args = new Bundle();
            args.putString("title", title);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view =  inflater.inflate(R.layout.fragment_compose_tweet, container);
            mEditText = view.findViewById(R.id.etTweet);
            mEditText.setOnEditorActionListener(this);
            return view;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            // Get field from view
            mEditText = (EditText) view.findViewById(R.id.etTweet);
            // Fetch arguments from bundle and set title
            String title = getArguments().getString("title", "Compose");
            getDialog().setTitle(title);
            // Show soft keyboard automatically and request focus to field
            mEditText.requestFocus();
            getDialog().getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }

        @Override
        public void onResume() {
            super.onResume();

//            DisplayMetrics displayMetrics = new DisplayMetrics();
//            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
////            int height = pxToDp(displayMetrics.heightPixels);
////            int width = pxToDp(displayMetrics.widthPixels);
//
//            int width = pxToDp(displayMetrics.widthPixels);
//            int height = pxToDp (displayMetrics.heightPixels);
//
//            getDialog().getWindow().setLayout(width,height);


            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }

    private int pxToDp(int px) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        float dp = px / (displayMetrics.densityDpi / 160f);
        return Math.round(dp);
    }

    public  int convertSpToPixels(int dp, Context context) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return px;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text back to activity through the implemented listener
            ComposeTweetDialogListener listener = (ComposeTweetDialogListener) getActivity();
            listener.onFinishEditDialog(mEditText.getText().toString());
            // Close the dialog and return back to the parent activity
            dismiss();
            return true;
        }
        return false;
    }
}
