package com.mymovies.launchpad.moviesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mymovies.launchpad.moviesapp.R;
import com.mymovies.launchpad.moviesapp.models.Video;
import com.mymovies.launchpad.moviesapp.models.Videos;
import com.mymovies.launchpad.moviesapp.utilities.Logging;

/**
 * Created by Dell on 11/11/2016.
 */

public class VideosListAdapter extends RecyclerView.Adapter<VideosListAdapter.MyViewHolder> {

    private Videos videos;
    private Context context;

    public VideosListAdapter(Videos videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.video_title);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_video, parent, false);
        return new VideosListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Video video = videos.get(position);
        holder.title.setText(video.getName());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newVideoPath = context.getString(R.string.url_video) + video.getKey();
                Logging.log(newVideoPath);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newVideoPath));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }
}
