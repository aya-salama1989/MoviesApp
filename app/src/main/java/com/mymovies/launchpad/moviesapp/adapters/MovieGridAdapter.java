package com.mymovies.launchpad.moviesapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.mymovies.launchpad.moviesapp.R;
import com.mymovies.launchpad.moviesapp.models.Movie;
import com.mymovies.launchpad.moviesapp.models.MoviesList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by hp on 10/8/2016.
 */

public class MovieGridAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private MoviesList movies;

    public MovieGridAdapter(Context context, MoviesList movies) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridViewHolder gridViewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item_movie, null);
            gridViewHolder = new GridViewHolder(convertView);
            convertView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    400));

            convertView.setTag(gridViewHolder);
        } else {
            gridViewHolder = (GridViewHolder) convertView.getTag();
        }

        String imgPath = context.getResources().getString(R.string.url_image)
                + movies.get(position).getPoster_path();
        Picasso.with(context).load(imgPath)
                .placeholder(android.R.drawable.alert_dark_frame).into(gridViewHolder.movieImage);

        return convertView;
    }

    class GridViewHolder {
        ImageView movieImage;

        GridViewHolder(View v) {
            movieImage = (ImageView) v.findViewById(R.id.grid_movie_poster);
        }
    }
}
