package com.mymovies.launchpad.moviesapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.mymovies.launchpad.moviesapp.R;
import com.mymovies.launchpad.moviesapp.activities.FavoritesActivity;
import com.mymovies.launchpad.moviesapp.adapters.MovieGridAdapter;
import com.mymovies.launchpad.moviesapp.models.Movie;
import com.mymovies.launchpad.moviesapp.models.MoviesList;

import java.util.ArrayList;

import static com.mymovies.launchpad.moviesapp.activities.MainActivity.mtwoPanel;
import static com.mymovies.launchpad.moviesapp.database.MoviesDBHelper.getDataBaseInstance;


public class FavoriteFragment extends Fragment {
    private GridView gridView;
    private MovieGridAdapter adapter;
    private View v;
    private MoviesList movies;
    private int mMoviePosition = gridView.INVALID_POSITION;
    private MainFragment.FragmentDataInterchange fragmentDataInterchange;
    private Movie movie;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_favorite, container, false);
        getData();
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
        super.onSaveInstanceState(outState);
        outState.putInt("itemPosition", mMoviePosition);
    }

    private void getData() {
        movies = getDataBaseInstance(getActivity()).getAllMovies();

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

        gridView = (GridView) v.findViewById(R.id.fav_movie_grid);
        adapter = new MovieGridAdapter(getActivity(), movies);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                movie = new Movie();
                movie.setId(movies.get(position).getId());
                movie.setVote_average(movies.get(position).getVote_average());
                movie.setTitle(movies.get(position).getTitle());
                movie.setRelease_date(movies.get(position).getRelease_date());
                movie.setPoster_path(movies.get(position).getPoster_path());
                mMoviePosition = position;
                fragmentDataInterchange.onItemSelected(movie);
            }
        });

        if (mMoviePosition != ListView.INVALID_POSITION) {
            gridView.smoothScrollToPosition(mMoviePosition);
        }

        if (movies.isEmpty()) {
            TextView txtViewNoFavs = (TextView) v.findViewById(R.id.no_fav_txt);
            txtViewNoFavs.setVisibility(View.VISIBLE);
        }
    }


}
