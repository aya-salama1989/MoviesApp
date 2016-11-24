package com.mymovies.launchpad.moviesapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.mymovies.launchpad.moviesapp.R;
import com.mymovies.launchpad.moviesapp.adapters.MovieGridAdapter;
import com.mymovies.launchpad.moviesapp.models.Movie;
import com.mymovies.launchpad.moviesapp.utilities.Logging;

import java.util.ArrayList;

import static com.mymovies.launchpad.moviesapp.database.MoviesDBHelper.getDataBaseInstance;


public class FavoriteFragment extends Fragment {
    private GridView gridView;
    private MovieGridAdapter adapter;
    private View v;
    private ArrayList<Movie> movies;
//    private MainFragment.FragmentDataInterchange fragmentDataInterchange;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_favorite, container, false);
        getData();
        initViews();
        return v;
    }

//    public void setFragmentDataInterchange(MainFragment.FragmentDataInterchange fragmentDataInterchange) {
//        this.fragmentDataInterchange = fragmentDataInterchange;
//    }


    private void getData() {
        movies = getDataBaseInstance(getActivity()).getAllMovies();
    }

    private void initViews() {
        gridView = (GridView) v.findViewById(R.id.fav_movie_grid);
        adapter = new MovieGridAdapter(getActivity(), movies);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Logging.log("item clicked: " + position);
                Movie movie = new Movie();
                movie.setId(movie.getId());
                movie.setVote_average(movie.getVote_average());
                movie.setTitle(movie.getTitle());
                movie.setRelease_date(movie.getRelease_date());
                movie.setPoster_path(movie.getPoster_path());
//                fragmentDataInterchange.onItemSelected(movie);
            }
        });

        if (movies.isEmpty()) {
            TextView txtViewNoFavs = (TextView) v.findViewById(R.id.no_fav_txt);
            txtViewNoFavs.setVisibility(View.VISIBLE);
        }
    }


}
