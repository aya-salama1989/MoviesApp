package com.mymovies.launchpad.moviesapp.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mymovies.launchpad.moviesapp.R;
import com.mymovies.launchpad.moviesapp.adapters.FavoritesCursorAdapter;
import com.mymovies.launchpad.moviesapp.adapters.MovieGridAdapter;
import com.mymovies.launchpad.moviesapp.database.MoviesContract;
import com.mymovies.launchpad.moviesapp.models.Movie;
import com.mymovies.launchpad.moviesapp.models.MoviesList;
import com.mymovies.launchpad.moviesapp.utilities.Logging;

import static com.mymovies.launchpad.moviesapp.activities.MainActivity.mtwoPanel;


public class FavoriteFragment extends Fragment implements FavoritesCursorAdapter.OnFavoriteClickListener {
    private static final int TASK_LOADER_ID = 0;
    private RecyclerView gridView;
    private MovieGridAdapter adapter;
    private View v;
    private MoviesList movies;
    private int mMoviePosition = gridView.NO_POSITION;
    private MainFragment.FragmentDataInterchange fragmentDataInterchange;
    private Movie movie;
    private FavoritesCursorAdapter favoritesCursorAdapter;
    private RecyclerView.LayoutManager gridLayoutManager;
    private Cursor mCursor;

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

    @Override
    public void onResume() {
        super.onResume();

        getData();
    }

    public void setFragmentDataInterchange(MainFragment.FragmentDataInterchange fragmentDataInterchange) {
        this.fragmentDataInterchange = fragmentDataInterchange;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("itemPosition", mMoviePosition);
    }

    private void getData() {
        mCursor = getActivity().getContentResolver().query(MoviesContract.MovieEntry.MOVIES_CONTENT_URI,
                null,
                null,
                null,
                null);
//        movies = getDataBaseInstance(getActivity()).getAllMovies();

        favoritesCursorAdapter = new FavoritesCursorAdapter(this, mCursor);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
//        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        gridView.setLayoutManager(gridLayoutManager);
        gridView.setHasFixedSize(true);
        gridView.setAdapter(favoritesCursorAdapter);

        Logging.log("items count: " + mCursor.getCount());

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mtwoPanel & mMoviePosition < 0) {
            movie = new Movie();
            movie.setId(movies.get(0).getId());
            movie.setTitle(movies.get(0).getTitle());
            movie.setVote_average(movies.get(0).getVote_average());
            movie.setPoster_path(movies.get(0).getPoster_path());
            movie.setRelease_date(movies.get(0).getRelease_date());
            movie.setOverview(movies.get(0).getOverview());
            if (fragmentDataInterchange != null) {
                fragmentDataInterchange.onItemSelected(movie);
            }
        }
    }

    private void initViews() {

        gridView = (RecyclerView) v.findViewById(R.id.rv_favorits);

//        adapter = new MovieGridAdapter(getActivity(), movies);
//        gridView.setAdapter(adapter);

//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                movie = new Movie();
//                movie.setId(movies.get(position).getId());
//                movie.setVote_average(movies.get(position).getVote_average());
//                movie.setTitle(movies.get(position).getTitle());
//                movie.setRelease_date(movies.get(position).getRelease_date());
//                movie.setPoster_path(movies.get(position).getPoster_path());
//                mMoviePosition = position;
//                fragmentDataInterchange.onItemSelected(movie);
//            }
//        });

        if (mMoviePosition != gridView.NO_POSITION) {
            gridView.smoothScrollToPosition(mMoviePosition);
        }

//        if (movies.isEmpty()) {
//            TextView txtViewNoFavs = (TextView) v.findViewById(R.id.no_fav_txt);
//            txtViewNoFavs.setVisibility(View.VISIBLE);
//        }

    }


    @Override
    public void onFavoriteCLick(int itemPosition, Movie movie) {
        movie.setId(movie.getId());
        movie.setTitle(movie.getTitle());
        movie.setVote_average(movie.getVote_average());
        movie.setPoster_path(movie.getPoster_path());
        movie.setOverview(movie.getOverview());
        movie.setRelease_date(movie.getRelease_date());
        fragmentDataInterchange.onItemSelected(movie);
    }
}
