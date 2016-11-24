package com.mymovies.launchpad.moviesapp.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.mymovies.launchpad.moviesapp.R;
import com.mymovies.launchpad.moviesapp.fragments.DetailsFragment;
import com.mymovies.launchpad.moviesapp.fragments.FavoriteFragment;
import com.mymovies.launchpad.moviesapp.fragments.MainFragment;
import com.mymovies.launchpad.moviesapp.models.Movie;

public class FavoritesActivity extends AppCompatActivity implements MainFragment.FragmentDataInterchange {
    private boolean mtwoPanel;
    public static final String FAVORIYE_DETAILS_FRAGMENT = "fav_details_frag";
    private DetailsFragment detailsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        setToolBar();

        if (findViewById(R.id.favorite_detail_container) != null) {
            mtwoPanel = true;
            if (savedInstanceState == null) {
                detailsFragment = new DetailsFragment();

                getFragmentManager().beginTransaction()
                        .replace(R.id.favorite_detail_container,
                                detailsFragment, FAVORIYE_DETAILS_FRAGMENT).commit();
            }
        } else {
            mtwoPanel = false;
        }
        FavoriteFragment favorites = new FavoriteFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.fav_fragment_holder, favorites).commit();
//        favorites.setFragmentDataInterchange(this);
    }

    private void setToolBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onItemSelected(Movie movie) {
//        Logging.log("movie: "+movie.getTitle().toString());
    }
}
