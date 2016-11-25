package com.mymovies.launchpad.moviesapp.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.mymovies.launchpad.moviesapp.R;
import com.mymovies.launchpad.moviesapp.fragments.DetailsFragment;
import com.mymovies.launchpad.moviesapp.fragments.MainFragment;
import com.mymovies.launchpad.moviesapp.models.Movie;
import com.mymovies.launchpad.moviesapp.utilities.Logging;

import static com.mymovies.launchpad.moviesapp.utilities.InternetConnectivity.checkOnlineState;

public class MainActivity extends AppCompatActivity implements MainFragment.FragmentDataInterchange {

    public static final String DETAILS_FRAGMENT = "details_frag";
    private DetailsFragment detailsFragment;
    public static boolean mtwoPanel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!checkOnlineState(getBaseContext())) {
            Logging.Toast(this, "Seems Like you Do not Have an Internet Connection");
            return;
        }


        if (findViewById(R.id.movie_detail_container) != null) {
            detailsFragment = new DetailsFragment();
            mtwoPanel = true;
            if (savedInstanceState == null) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.movie_detail_container, detailsFragment, DETAILS_FRAGMENT);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        } else {
            mtwoPanel = false;
        }

        MainFragment mainFragment = (MainFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_movies);
        mainFragment.setFragmentDataInterchange(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.action_favorites:
                Intent intent = new Intent(this, FavoritesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie_data", movie);
        if (mtwoPanel) {
            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(bundle);

            getFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container,
                            detailsFragment, DETAILS_FRAGMENT).commit();

        } else {
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }


    }
}
