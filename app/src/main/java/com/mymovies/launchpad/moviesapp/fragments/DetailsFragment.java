package com.mymovies.launchpad.moviesapp.fragments;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mymovies.launchpad.moviesapp.R;
import com.mymovies.launchpad.moviesapp.adapters.ReviewsListAdapter;
import com.mymovies.launchpad.moviesapp.adapters.VideosListAdapter;
import com.mymovies.launchpad.moviesapp.backendControllers.ReviewsDataFetcher;
import com.mymovies.launchpad.moviesapp.backendControllers.VideosDataFetcher;
import com.mymovies.launchpad.moviesapp.database.MoviesContract;
import com.mymovies.launchpad.moviesapp.models.Movie;
import com.mymovies.launchpad.moviesapp.models.Reviews;
import com.mymovies.launchpad.moviesapp.models.Videos;
import com.mymovies.launchpad.moviesapp.utilities.Logging;
import com.squareup.picasso.Picasso;

public class DetailsFragment extends Fragment implements View.OnClickListener,
        VideosDataFetcher.VideosDataFetcherListener,
        ReviewsDataFetcher.ReviewsFetcherListener {

    Button btnAddToFavorite;
    private String movieTitle, movieImg, releaseDate, movieRating, movieOverView;
    private View v;
    private Movie mMovie;
    private VideosListAdapter videosListAdapter;
    private Videos mVideos;
    private VideosDataFetcher videosDataFetcher;

    private Reviews mReviews;
    private ReviewsDataFetcher dataFetcher;
    private ReviewsListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_details, container, false);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mMovie = getArguments().getParcelable("movie_data");
        }
        if (mMovie != null) {
            getData();
            initViews();
        }
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_details, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            case R.id.action_share:
                if (mVideos != null) {
                    String videoURL = getString(R.string.url_video) + mVideos.get(0).getKey();
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, videoURL);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void getData() {

        mVideos = new Videos();
        mReviews = new Reviews();

        movieTitle = mMovie.getTitle();
        movieImg = getResources().getString(R.string.url_image) + mMovie.getPoster_path();
        releaseDate = mMovie.getRelease_date();
        movieRating = mMovie.getVote_average();
        movieOverView = mMovie.getOverview();

        videosDataFetcher = new VideosDataFetcher(getActivity(), this);
        videosDataFetcher.getVideos(String.valueOf(mMovie.getId()));

        dataFetcher = new ReviewsDataFetcher(getActivity(), this);
        dataFetcher.getReviews(String.valueOf(mMovie.getId()));
    }

    private void initViews() {
        TextView titleTxt = v.findViewById(R.id.movieTitle);
        TextView releaseDateTxt = v.findViewById(R.id.releaseDate);
        TextView ratingTxt = v.findViewById(R.id.movieRating);
        TextView overviewTxt = v.findViewById(R.id.movieOverView);
        ImageView movieImgVue = v.findViewById(R.id.movieImg);
         btnAddToFavorite = v.findViewById(R.id.add_fav);
        RecyclerView videosListView = v.findViewById(R.id.list_trailers);
        RecyclerView reviewsListView = v.findViewById(R.id.list_reviews);
        // setting movie poster
        Picasso.with(getActivity()).load(movieImg).into(movieImgVue);

        // setting movie title
//        titleTxt = (TextView) v.findViewById(R.id.movieTitle);
        titleTxt.setText(movieTitle);

        // setting movie release Date
        releaseDateTxt.setText(getString(R.string.release_Date, releaseDate));

        // setting movie Rating
        ratingTxt.setText(getString(R.string.rating, movieRating));

        // setting movie over View
        overviewTxt.setText(movieOverView);

        // Set and Custom Add to Favorite Button
        btnAddToFavorite.setOnClickListener(this);
        //1- TODO: get from content providers
        Cursor cursor = getActivity().getContentResolver().query(MoviesContract.MovieEntry.MOVIES_CONTENT_URI, null,
                MoviesContract.MovieEntry.MOVIE_ID + "=?"
                , new String[]{String.valueOf(mMovie.getId())}, null);
        if (cursor.getCount() != 0) {
            btnAddToFavorite.setText(getString(R.string.mark_not_favorite));
        } else {
            btnAddToFavorite.setText(getString(R.string.mark_favorite));
        }

        // Initiate and populate videos recyclerView
        videosListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        videosListAdapter = new VideosListAdapter(mVideos, getActivity());
        videosListView.setAdapter(videosListAdapter);

        // Initiate and populate reviews recyclerView
        reviewsListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ReviewsListAdapter(mReviews, getActivity());
        reviewsListView.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_fav:
                String imgPath = movieImg.substring(movieImg.lastIndexOf("/"), movieImg.length());
                mMovie.setPoster_path(imgPath);
                if (btnAddToFavorite.getText().toString().equalsIgnoreCase(getString(R.string.mark_not_favorite))) {
                    Uri uri = MoviesContract.MovieEntry.MOVIES_CONTENT_URI.buildUpon()
                            .appendPath(String.valueOf(mMovie.getId())).build();
                    Logging.log("My URI-1: " + uri);

                    getActivity().getContentResolver()
                            .delete(uri, null, null);
                    if (uri != null) {
                        Logging.log("My URI: " + uri);
                    }

                    btnAddToFavorite.setText(getString(R.string.mark_favorite));

                } else if (btnAddToFavorite.getText().toString().equalsIgnoreCase(getString(R.string.mark_favorite))) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MoviesContract.MovieEntry.MOVIE_ID, mMovie.getId());
                    contentValues.put(MoviesContract.MovieEntry.MOVIE_TITLE, mMovie.getTitle());
                    contentValues.put(MoviesContract.MovieEntry.MOVIE_POSTER, mMovie.getPoster_path());
                    contentValues.put(MoviesContract.MovieEntry.MOVIE_RELEASE_DATE, mMovie.getRelease_date());
                    Uri uri = getActivity().getContentResolver()
                            .insert(MoviesContract.MovieEntry.MOVIES_CONTENT_URI, contentValues);
                    Logging.log("My URI: " + uri);
                    if (uri != null) {
                        btnAddToFavorite.setText(getString(R.string.mark_not_favorite));
                    }
                } else {
                    return;
                }

                break;
            default:
        }
    }

    @Override
    public void onConnectionDone(Reviews reviews) {
        mReviews.addAll(reviews);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onConnectionDone(Videos videos) {
        mVideos.addAll(videos);
        videosListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onConnectionFailed() {
        Logging.Toast(getActivity(), getString(R.string.no_internet));
    }
}
