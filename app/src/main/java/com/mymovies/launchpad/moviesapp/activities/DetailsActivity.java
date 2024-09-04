package com.mymovies.launchpad.moviesapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.mymovies.launchpad.moviesapp.R;
import com.mymovies.launchpad.moviesapp.fragments.DetailsFragment;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setToolBar();

        Bundle b = getIntent().getExtras();

        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(b);

        getFragmentManager().beginTransaction()
                .replace(R.id.activity_details, detailsFragment).commit();
    }

    private void setToolBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        }
    }

    // Newer, but Im not sure what API version it came in
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // Older, still supported

}
