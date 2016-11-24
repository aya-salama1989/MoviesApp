package com.mymovies.launchpad.moviesapp;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.mymovies.launchpad.moviesapp.database.MovieProvider;
import com.mymovies.launchpad.moviesapp.database.MoviesContract;

import static com.mymovies.launchpad.moviesapp.database.MoviesContract.PATH_MOVIE_ID;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Dell on 21/11/2016.
 */

public class TestURIMatchers extends AndroidTestCase {
    private static int TEST_MOVIE_ID = 278;

    private static final Uri TEST_MOVIE_DIR = MoviesContract.MovieEntry.MOVIES_CONTENT_URI;
    private static final Uri TEST_MOVIES_ID = MoviesContract.MovieEntry.MOVIES_CONTENT_URI.buildUpon()
            .appendPath(PATH_MOVIE_ID).build();

    public void testUriMatcher() {
        UriMatcher testMatcher = MovieProvider.buildUriMatcher();

        assertEquals("Error: The WEATHER URI was matched incorrectly.",
                testMatcher.match(TEST_MOVIE_DIR), MovieProvider.MOVIES);
        assertEquals("Error: The WEATHER WITH LOCATION URI was matched incorrectly.",
                testMatcher.match(TEST_MOVIES_ID), MovieProvider.MOVIE_BY_ID);

    }
}
