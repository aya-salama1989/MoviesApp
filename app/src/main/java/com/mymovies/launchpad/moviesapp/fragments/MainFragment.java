package com.mymovies.launchpad.moviesapp.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.mymovies.launchpad.moviesapp.R;
import com.mymovies.launchpad.moviesapp.adapters.MovieGridAdapter;
import com.mymovies.launchpad.moviesapp.backendControllers.MoviesDataFetcher;
import com.mymovies.launchpad.moviesapp.models.Movie;
import com.mymovies.launchpad.moviesapp.models.MoviesList;
import com.mymovies.launchpad.moviesapp.utilities.Logging;

import static com.mymovies.launchpad.moviesapp.activities.MainActivity.mtwoPanel;


public class MainFragment extends Fragment implements MoviesDataFetcher.DataFetcherListener {

    private MoviesDataFetcher moviesDataFetcher;
    private GridView gridView;
    private MovieGridAdapter adapter;
    private MoviesList moviesList;
    private SharedPreferences sharedPreferences;
    private FragmentDataInterchange fragmentDataInterchange;
    private int mMoviePosition = gridView.INVALID_POSITION;
    private View v;
    private Movie movie;
    private static final String SELECTED_KEY = "selected_position";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_main, container, false);
        moviesList = new MoviesList();
        setData();
        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mMoviePosition = savedInstanceState.getInt(SELECTED_KEY);
        }
        initViews();
        return v;
    }

    public void setFragmentDataInterchange(FragmentDataInterchange fragmentDataInterchange) {
        this.fragmentDataInterchange = fragmentDataInterchange;
    }

    private void initViews() {
        gridView = (GridView) v.findViewById(R.id.movie_grid);
        adapter = new MovieGridAdapter(getActivity(), moviesList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                movie = new Movie();
                movie.setId(moviesList.get(position).getId());
                movie.setTitle(moviesList.get(position).getTitle());
                movie.setVote_average(moviesList.get(position).getVote_average());
                movie.setPoster_path(moviesList.get(position).getPoster_path());
                movie.setOverview(moviesList.get(position).getOverview());
                movie.setRelease_date(moviesList.get(position).getRelease_date());
                mMoviePosition = position;
                fragmentDataInterchange.onItemSelected(movie);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mMoviePosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mMoviePosition);
        }
        super.onSaveInstanceState(outState);
    }


    public interface FragmentDataInterchange {
        void onItemSelected(Movie movie);
    }

    private void setData() {
        PreferenceManager.setDefaultValues(getActivity(), R.xml.settings, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String searchQuery = sharedPreferences.getString(getString(R.string.sortType), null);
        moviesDataFetcher = new MoviesDataFetcher(getActivity(), this);
        moviesDataFetcher.search(searchQuery);
    }

    @Override
    public void onConnectionFailed() {
        Logging.Toast(getActivity(), "Seems like you're having a problem connecting to the internet");
    }

    @Override
    public void onConnectionDone(MoviesList movies) {
        if (mtwoPanel & mMoviePosition < 0) {
            mMoviePosition = 0;
            movie = new Movie();
            movie.setId(movies.get(mMoviePosition).getId());
            movie.setTitle(movies.get(mMoviePosition).getTitle());
            movie.setVote_average(movies.get(mMoviePosition).getVote_average());
            movie.setPoster_path(movies.get(mMoviePosition).getPoster_path());
            movie.setOverview(movies.get(mMoviePosition).getOverview());
            movie.setRelease_date(movies.get(mMoviePosition).getRelease_date());
            if (fragmentDataInterchange != null) {
                fragmentDataInterchange.onItemSelected(movie);
            }
        }

        for (int i = 0; i < movies.size(); i++) {
            moviesList.add(movies.get(i));
        }

        adapter.notifyDataSetChanged();

        if (mMoviePosition != ListView.INVALID_POSITION) {
            gridView.smoothScrollToPosition(mMoviePosition);
        }
    }
}
