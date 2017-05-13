package com.mymovies.launchpad.moviesapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mymovies.launchpad.moviesapp.R;
import com.mymovies.launchpad.moviesapp.database.MoviesContract;
import com.mymovies.launchpad.moviesapp.models.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by Dell on 12/05/2017.
 */

public class FavoritesCursorAdapter extends RecyclerView.Adapter<FavoritesCursorAdapter.MovieViewHolder> {
    private Cursor mCursor;
    private Context mContext;
    private OnFavoriteClickListener onFavoriteClickListener;
    private MovieViewHolder movieViewHolder;

    public FavoritesCursorAdapter(OnFavoriteClickListener onFavoriteClickListener, Cursor mCursor) {
        this.onFavoriteClickListener = onFavoriteClickListener;
        this.mCursor = mCursor;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.grid_item_movie, parent, false);
        movieViewHolder = new MovieViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        Movie movie = new Movie();
        // Indices for the _id, description, and priority columns
        int idIndex = mCursor.getColumnIndex(MoviesContract.MovieEntry.MOVIE_ID);
        int movieTitle = mCursor.getColumnIndex(MoviesContract.MovieEntry.MOVIE_TITLE);
        int movieDate = mCursor.getColumnIndex(MoviesContract.MovieEntry.MOVIE_RELEASE_DATE);
        int movieRate = mCursor.getColumnIndex(MoviesContract.MovieEntry.MOVIE_RATING);
        int posterPathe =  mCursor.getColumnIndex(MoviesContract.MovieEntry.MOVIE_POSTER);

//TODO: get poster,
        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
        movie.setId(mCursor.getInt(idIndex));
        movie.setTitle(mCursor.getString(movieTitle));
        movie.setRelease_date(mCursor.getString(movieDate));
        movie.setVote_average(mCursor.getString(movieRate));
        movie.setPoster_path(mCursor.getString(posterPathe));


        movieViewHolder.setMovieData(movie);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public interface OnFavoriteClickListener {
        void onFavoriteCLick(int itemPosition, Movie movie);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView moviePoster;
        Movie mMovie;

        public MovieViewHolder(View view) {
            super(view);
            moviePoster = (ImageView) view.findViewById(R.id.grid_movie_poster);
            view.setOnClickListener(this);

        }

        public void setMovieData(Movie movie) {
            mMovie = movie;
            Picasso.with(mContext)
                    .load(mContext.getString(R.string.url_image) + movie.getPoster_path())
                    .placeholder(R.drawable.reviews)
                    .error(R.drawable.reviews)
                    .into(moviePoster);
        }


        @Override
        public void onClick(View v) {
            int itemPosition = getAdapterPosition();
            onFavoriteClickListener.onFavoriteCLick(itemPosition, mMovie);
        }
    }
}
