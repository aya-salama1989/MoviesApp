package com.mymovies.launchpad.moviesapp.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mymovies.launchpad.moviesapp.R;
import com.mymovies.launchpad.moviesapp.adapters.MoviesGridRecycler;
import com.mymovies.launchpad.moviesapp.backendControllers.MoviesDataFetcher;
import com.mymovies.launchpad.moviesapp.models.Movie;
import com.mymovies.launchpad.moviesapp.models.MoviesList;
import com.mymovies.launchpad.moviesapp.utilities.Logging;


public class MainFragment extends Fragment implements MoviesDataFetcher.DataFetcherListener, MoviesGridRecycler.OnMovieItemClickListener {

    private static final String SELECTED_KEY = "selected_position";
    private MoviesDataFetcher moviesDataFetcher;

    private MoviesGridRecycler moviesGridRecycler;
    private RecyclerView moviesRecycler;
    private MoviesList moviesList;
    private SharedPreferences sharedPreferences;
    private FragmentDataInterchange fragmentDataInterchange;


    private View v;
    private Movie movie;
    private String searchQuery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_main, container, false);
        moviesList = new MoviesList();
        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
//            mMoviePosition = savedInstanceState.getInt(SELECTED_KEY);
        }
        initViews();
        return v;
    }

    public void setFragmentDataInterchange(FragmentDataInterchange fragmentDataInterchange) {
        this.fragmentDataInterchange = fragmentDataInterchange;
    }


    private void initViews() {
        moviesGridRecycler = new MoviesGridRecycler(moviesList, this);
        moviesRecycler = (RecyclerView) v.findViewById(R.id.rv_movies);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        moviesRecycler.setLayoutManager(gridLayoutManager);
        moviesRecycler.setHasFixedSize(true);
        moviesRecycler.setAdapter(moviesGridRecycler);
//
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
//        if (mMoviePosition != ListView.INVALID_POSITION) {
//            outState.putInt(SELECTED_KEY, mMoviePosition);
//        }
        super.onSaveInstanceState(outState);
    }

    private void setData() {
        PreferenceManager.setDefaultValues(getActivity(), R.xml.settings, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.OnSharedPreferenceChangeListener listener
                = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                // Implementation
            }
        };

        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);

        searchQuery = sharedPreferences.getString(getString(R.string.sortType), null);
        moviesDataFetcher = new MoviesDataFetcher(getActivity(), this);
        moviesDataFetcher.search(searchQuery);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (moviesList.isEmpty()) {
            setData();
        } else {
            // nothing
        }
    }


    @Override
    public void onConnectionFailed() {
        Logging.Toast(getActivity(), getString(R.string.no_internet));
    }

    @Override
    public void onConnectionDone(MoviesList movies) {
        moviesList.addAll(movies);
        moviesGridRecycler.notifyDataSetChanged();


//        if (mtwoPanel & mMoviePosition < 0) {
//            mMoviePosition = 0;
//            movie = new Movie();
//            movie.setId(movies.get(mMoviePosition).getId());
//            movie.setTitle(movies.get(mMoviePosition).getTitle());
//            movie.setVote_average(movies.get(mMoviePosition).getVote_average());
//            movie.setPoster_path(movies.get(mMoviePosition).getPoster_path());
//            movie.setOverview(movies.get(mMoviePosition).getOverview());
//            movie.setRelease_date(movies.get(mMoviePosition).getRelease_date());
//            if (fragmentDataInterchange != null) {
//                fragmentDataInterchange.onItemSelected(movie);
//            }
//        }
//
//        for (int i = 0; i < movies.size(); i++) {
//            moviesList.add(movies.get(i));
//        }

//        adapter.notifyDataSetChanged();
//
//        if (mMoviePosition != ListView.INVALID_POSITION) {
//            gridView.smoothScrollToPosition(mMoviePosition);
//        }
    }

    @Override
    public void onMovieClick(int itemPosition) {
        movie = new Movie();
        movie.setId(moviesList.get(itemPosition).getId());
        movie.setTitle(moviesList.get(itemPosition).getTitle());
        movie.setVote_average(moviesList.get(itemPosition).getVote_average());
        movie.setPoster_path(moviesList.get(itemPosition).getPoster_path());
        movie.setOverview(moviesList.get(itemPosition).getOverview());
        movie.setRelease_date(moviesList.get(itemPosition).getRelease_date());
        fragmentDataInterchange.onItemSelected(movie);
    }

    public interface FragmentDataInterchange {
        void onItemSelected(Movie movie);
    }
}
