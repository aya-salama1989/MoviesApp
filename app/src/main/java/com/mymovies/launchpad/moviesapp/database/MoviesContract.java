package com.mymovies.launchpad.moviesapp.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Dell on 12/11/2016.
 */

public class MoviesContract {

    //  "Content authority"
    public static final String CONTENT_AUTHORITY = "com.mymovies.launchpad.moviesapp";
    // base URI
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    // Movie path
    public static final String PATH_MOVIE = "movies_table";
    public static final String PATH_MOVIE_ID = "movies_table/";



    public static final class MovieEntry implements BaseColumns {

        public static final Uri MOVIES_CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String TABLE_MOVIES = "movies_table";
        public static final String MOVIE_ID = "movie_id";
        public static final String MOVIE_TITLE = "movie_title";
        public static final String MOVIE_POSTER = "movie_poster";
        public static final String MOVIE_RELEASE_DATE = "movie_date";
        public static final String MOVIE_OVER_VIEW = "movie_overview";
        public static final String MOVIE_RATING = "movie_rating";


    }
}
