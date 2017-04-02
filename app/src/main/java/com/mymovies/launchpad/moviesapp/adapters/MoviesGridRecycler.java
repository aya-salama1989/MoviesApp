package com.mymovies.launchpad.moviesapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mymovies.launchpad.moviesapp.R;
import com.mymovies.launchpad.moviesapp.models.Movie;
import com.mymovies.launchpad.moviesapp.models.MoviesList;
import com.squareup.picasso.Picasso;

/**
 * Created by Dell on 31/03/2017.
 */

public class MoviesGridRecycler extends RecyclerView.Adapter<MoviesGridRecycler.MovieViewHolder> {
    final private OnMovieItemClickListener mMovieClickListener;
    private Context context;
    private MoviesList movies;
    private MovieViewHolder movieViewHolder;

    public MoviesGridRecycler(MoviesList movies, OnMovieItemClickListener mMovieClickListener) {
        this.movies = movies;
        this.mMovieClickListener = mMovieClickListener;
        setHasStableIds(true);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.grid_item_movie, parent, false);
        movieViewHolder = new MovieViewHolder(view);
        return movieViewHolder;
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Log.e("TAG", String.valueOf(position));
        Movie movie = movies.get(position);
        movieViewHolder.setMoviePoster(movie.getPoster_path());
//        holder.isRecyclable();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public interface OnMovieItemClickListener {
        void onMovieClick(int itemPosition);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView moviePoster;

        public MovieViewHolder(View view) {
            super(view);
            moviePoster = (ImageView) view.findViewById(R.id.grid_movie_poster);
            view.setOnClickListener(this);
        }

        public void setMoviePoster(String url) {
            Picasso.with(context).load(context.getString(R.string.url_image) + url)
                    .placeholder(R.drawable.reviews).error(R.drawable.reviews).into(movieViewHolder.moviePoster);
        }


        @Override
        public void onClick(View v) {
            int itemPosition = getAdapterPosition();
            mMovieClickListener.onMovieClick(itemPosition);
        }
    }
}
