package com.mymovies.launchpad.moviesapp.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mymovies.launchpad.moviesapp.R;
import com.mymovies.launchpad.moviesapp.adapters.FavoritesCursorAdapter;
import com.mymovies.launchpad.moviesapp.database.MoviesContract;
import com.mymovies.launchpad.moviesapp.models.Movie;
import com.mymovies.launchpad.moviesapp.utilities.Logging;

import static com.mymovies.launchpad.moviesapp.activities.MainActivity.mtwoPanel;


public class FavoriteFragment extends Fragment implements FavoritesCursorAdapter.OnFavoriteClickListener {

    private RecyclerView gridView;
    private View v;
    private int mMoviePosition = gridView.NO_POSITION;
    private MainFragment.FragmentDataInterchange fragmentDataInterchange;
    private Movie movie;
    private FavoritesCursorAdapter favoritesCursorAdapter;
    private RecyclerView.LayoutManager gridLayoutManager;
    private Cursor mCursor;
    private TextView txtViewNoFavs;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_favorite, container, false);

        if (savedInstanceState != null && savedInstanceState.containsKey("itemPosition")) {
            mMoviePosition = savedInstanceState.getInt("itemPosition");
        }
        initViews();


        return v;
    }

    public void setFragmentDataInterchange(MainFragment.FragmentDataInterchange fragmentDataInterchange) {
        this.fragmentDataInterchange = fragmentDataInterchange;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("itemPosition", mMoviePosition);
        super.onSaveInstanceState(outState);

    }

    private void getData() {
        mCursor = getActivity().getContentResolver().query(MoviesContract.MovieEntry.MOVIES_CONTENT_URI,
                null,
                null,
                null,
                null);

        favoritesCursorAdapter = new FavoritesCursorAdapter(this, mCursor);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridView.setLayoutManager(gridLayoutManager);
        gridView.setHasFixedSize(true);
        gridView.setAdapter(favoritesCursorAdapter);

        if (mtwoPanel & mMoviePosition < 0) {
            gridView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    gridView.findViewHolderForAdapterPosition(0).itemView.performClick();
                }
            }, 400);
        }


        if (mMoviePosition != gridView.NO_POSITION) {
            gridLayoutManager.smoothScrollToPosition(gridView, null, mMoviePosition);
        }

        Logging.log("items count: " + mCursor.getCount());
//        if (mCursor.getCount() == 0) {
//            txtViewNoFavs = (TextView) v.findViewById(R.id.no_fav_txt);
//            txtViewNoFavs.setVisibility(View.VISIBLE);
//        } else {
//            txtViewNoFavs.setVisibility(View.INVISIBLE);
//        }

//        if (mtwoPanel & mMoviePosition < 0) {
//            gridView.findViewHolderForAdapterPosition(0).itemView.performClick();
//        }

    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
        //TODO: check positions to set a defaultly set item

    }

    private void initViews() {
        gridView = (RecyclerView) v.findViewById(R.id.rv_favorits);


    }


    @Override
    public void onFavoriteCLick(int itemPosition, Movie movie) {
        mMoviePosition = itemPosition;
        movie.setId(movie.getId());
        movie.setTitle(movie.getTitle());
        movie.setVote_average(movie.getVote_average());
        movie.setPoster_path(movie.getPoster_path());
        movie.setOverview(movie.getOverview());
        movie.setRelease_date(movie.getRelease_date());
        //TODO: a problem in the interface
        fragmentDataInterchange.onItemSelected(movie);
    }
}
