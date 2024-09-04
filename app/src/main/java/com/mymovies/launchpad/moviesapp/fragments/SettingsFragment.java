package com.mymovies.launchpad.moviesapp.fragments;


import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.mymovies.launchpad.moviesapp.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);
    }
}
