package com.codepath.apps.twitterclient.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.twitterclient.R;

import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;


/**
 * Created by manoj on 9/30/17.
 */

public class ComposeDialogueFragment extends DialogFragment implements TextView.OnEditorActionListener {

    private EditText mEditText;
    private Button cancel;
    private TextView charCount;
    private static final String TAG = "ComposeDialogueFragment";

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
        View view = inflater.inflate(R.layout.fragment_compose_tweet, container);
        mEditText = view.findViewById(R.id.etTweet);
        mEditText.setOnEditorActionListener(this);
        charCount = view.findViewById(R.id.tvCharCount);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.etTweet);

        if (mEditText != null) {
            mEditText.setLines(5);
            mEditText.setHorizontallyScrolling(false);
        }

        cancel = view.findViewById(R.id.canceButton);

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Compose");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        getActivity().getWindow().setSoftInputMode(SOFT_INPUT_ADJUST_PAN);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                charCount.setText(String.valueOf(start));
                if (start > 140) {
                    mEditText.setTextColor(Color.parseColor("#FF0000"));
                    charCount.setTextColor(Color.parseColor("#FF0000"));
                } else {

                    mEditText.setTextColor(Color.parseColor("#FF000000"));
                    charCount.setTextColor(Color.parseColor("#008000"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        cancel.setOnClickListener(view -> dismiss());
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text back to activity through the implemented listener
            ComposeTweetDialogListener listener = (ComposeTweetDialogListener) getTargetFragment();
            listener.onFinishEditDialog(mEditText.getText().toString());
            // Close the dialog and return back to the parent activity
            dismiss();
            return true;
        }
        return false;
    }
}
