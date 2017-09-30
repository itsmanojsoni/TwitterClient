package com.codepath.apps.twitterclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.models.TwitterResponse;
import com.codepath.apps.twitterclient.models.User;
import com.codepath.apps.twitterclient.util.StringUtil;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by manoj on 9/29/17.
 */
public class TwitterFeedAdapter extends RecyclerView.Adapter<TwitterFeedAdapter.ViewHolder> {

    private static final String TAG = "TwitterFeedAdapter";

    private Context context;
    private List<TwitterResponse> list;
    private OnItemClickListener onItemClickListener;

    public TwitterFeedAdapter(Context context, List<TwitterResponse> list,
                              OnItemClickListener onItemClickListener) {
        Log.d(TAG, "Twitter Feed Adapter Created");
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Todo Butterknife bindings
        ImageView ivProfileImage;
        TextView tvStatusText;
        TextView tvScreenName;
        TextView tvUserName;
        TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvStatusText = itemView.findViewById(R.id.tvStatusText);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvTime  = itemView.findViewById(R.id.tvTime);
        }

        public void bind(final TwitterResponse model,
                         final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getLayoutPosition());

                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreteView Holder");
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.activity_feed_item, parent, false);
        ButterKnife.bind(this, view);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TwitterResponse item = list.get(position);

        Log.d(TAG, "onBindView Holder and position is : "+position);

        //Todo: Setup viewholder for item 
        holder.bind(item, onItemClickListener);
        User user = item.getUser();

        holder.tvUserName.setText(user.getName());
        holder.tvScreenName.setText(user.getScreen_name());
        holder.tvStatusText.setText(item.getText());
//        holder.tvTime.setText(StringUtil.formatShortHumanTimestamp(Long.valueOf(item.getCreated_at())));
        Glide.with(context).load(user.getProfile_image_url()).dontAnimate().into(holder.ivProfileImage);
    }


    @Override
    public int getItemCount() {
        Log.d(TAG, "Twitter Adapter Size = "+list.size());
        return list.size();
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}