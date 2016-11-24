package com.mymovies.launchpad.moviesapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mymovies.launchpad.moviesapp.R;
import com.mymovies.launchpad.moviesapp.adapters.ReviewsListAdapter;
import com.mymovies.launchpad.moviesapp.adapters.VideosListAdapter;
import com.mymovies.launchpad.moviesapp.backendControllers.ReviewsDataFetcher;
import com.mymovies.launchpad.moviesapp.backendControllers.VideosDataFetcher;
import com.mymovies.launchpad.moviesapp.models.Movie;
import com.mymovies.launchpad.moviesapp.models.Reviews;
import com.mymovies.launchpad.moviesapp.models.Videos;
import com.mymovies.launchpad.moviesapp.utilities.Logging;
import com.squareup.picasso.Picasso;

import static com.mymovies.launchpad.moviesapp.database.MoviesDBHelper.getDataBaseInstance;


public class DetailsFragment extends Fragment implements View.OnClickListener, VideosDataFetcher.VideosDataFetcherListener, ReviewsDataFetcher.ReviewsFetcherListener {
    private String movieTitle, movieImg, releaseDate, movieRating, movieOverView;
    private TextView titleTxt, releaseDateTxt, ratingTxt, overviewTxt;
    private ImageView movieImgVue;
    private View v;
    private Button btnAddToFavorite;
    private Movie mMovie;

    private RecyclerView videosListView, reviewsListView;

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

    //TODO: add share Functionality
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_details, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_share:
                String videoURL = getString(R.string.url_video) + mVideos.get(0).getKey();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, videoURL);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void getData() {

        mVideos = new Videos();
        mReviews = new Reviews();

        movieTitle = mMovie.getTitle();
        movieImg = mMovie.getPoster_path();
        releaseDate = mMovie.getRelease_date();
        movieRating = mMovie.getVote_average();
        movieOverView = mMovie.getOverview();

        videosDataFetcher = new VideosDataFetcher(getActivity(), this);
        videosDataFetcher.getVideos(String.valueOf(mMovie.getId()));

        dataFetcher = new ReviewsDataFetcher(getActivity(), this);
        dataFetcher.getReviews(String.valueOf(mMovie.getId()));
    }

    private void initViews() {
        // setting movie poster
        movieImgVue = (ImageView) v.findViewById(R.id.movieImg);
        Picasso.with(getActivity()).load(movieImg).into(movieImgVue);

        // setting movie title
        titleTxt = (TextView) v.findViewById(R.id.movieTitle);
        titleTxt.setText(movieTitle);

        // setting movie release Date
        releaseDateTxt = (TextView) v.findViewById(R.id.releaseDate);
        releaseDateTxt.setText("Release Date: " + releaseDate);

        // setting movie Rating
        ratingTxt = (TextView) v.findViewById(R.id.movieRating);
        ratingTxt.setText("Rating: " + movieRating);

        // setting movie over View
        overviewTxt = (TextView) v.findViewById(R.id.movieOverView);
        overviewTxt.setText(movieOverView);

        // Set and Custom Add to Favorite Button
        btnAddToFavorite = (Button) v.findViewById(R.id.add_fav);
        btnAddToFavorite.setOnClickListener(this);
        if (getDataBaseInstance(getActivity()).findMovie(String.valueOf(mMovie.getId()))) {
            btnAddToFavorite.setText("Marked Favorite");
        } else {
            btnAddToFavorite.setText("Add to Favorite");
        }

        // Initiate and populate videos recyclerView
        videosListView = (RecyclerView) v.findViewById(R.id.list_trailers);
        videosListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        videosListAdapter = new VideosListAdapter(mVideos, getActivity());
        videosListView.setAdapter(videosListAdapter);

        // Initiate and populate reviews recyclerView
        reviewsListView = (RecyclerView) v.findViewById(R.id.list_reviews);
        reviewsListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ReviewsListAdapter(mReviews, getActivity());
        reviewsListView.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_fav:
                mMovie.setPoster_path(movieImg.substring(movieImg.lastIndexOf("/"), movieImg.length()));
                getDataBaseInstance(getActivity()).insertMovie(mMovie);
                btnAddToFavorite.setText("Added to favourite");
                btnAddToFavorite.setOnClickListener(null);
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
        Logging.Toast(getActivity(), "Seems like you're having a problem connecting to the internet");
    }
}
