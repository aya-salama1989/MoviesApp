package com.mymovies.launchpad.moviesapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.mymovies.launchpad.moviesapp.models.Movie;
import com.mymovies.launchpad.moviesapp.models.MoviesList;
import com.mymovies.launchpad.moviesapp.utilities.Logging;

import static com.mymovies.launchpad.moviesapp.database.MoviesContract.MovieEntry.MOVIE_ID;
import static com.mymovies.launchpad.moviesapp.database.MoviesContract.MovieEntry.MOVIE_OVER_VIEW;
import static com.mymovies.launchpad.moviesapp.database.MoviesContract.MovieEntry.MOVIE_POSTER;
import static com.mymovies.launchpad.moviesapp.database.MoviesContract.MovieEntry.MOVIE_RATING;
import static com.mymovies.launchpad.moviesapp.database.MoviesContract.MovieEntry.MOVIE_RELEASE_DATE;
import static com.mymovies.launchpad.moviesapp.database.MoviesContract.MovieEntry.MOVIE_TITLE;
import static com.mymovies.launchpad.moviesapp.database.MoviesContract.MovieEntry.TABLE_MOVIES;

/**
 * Created by Dell on 12/11/2016.
 */

public class MoviesDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "movies.db";

    private static MoviesDBHelper dbInstance;


    private MoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static MoviesDBHelper getDataBaseInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new MoviesDBHelper(context);
        }
        return dbInstance;
    }

    private static final String CREATE_TABLA_MOVIE = "create table " + TABLE_MOVIES + "("
            + MOVIE_ID + " INTEGER PRIMARY KEY, " + MOVIE_TITLE + " TEXT, "
            + MOVIE_POSTER + " TEXT, " + MOVIE_RELEASE_DATE + " TEXT, "
            + MOVIE_OVER_VIEW + " TEXT, " + MOVIE_RATING + " TEXT);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLA_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE " + TABLE_MOVIES);
        onCreate(db);
    }


    // insert a movie
    public void insertMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues movieValues = new ContentValues();

        movieValues.put(MOVIE_ID, movie.getId());
        movieValues.put(MOVIE_TITLE, movie.getTitle());
        movieValues.put(MOVIE_POSTER, movie.getPoster_path());
        movieValues.put(MOVIE_RATING, movie.getVote_average());
        movieValues.put(MOVIE_RELEASE_DATE, movie.getRelease_date());
        movieValues.put(MOVIE_OVER_VIEW, movie.getOverview());
        if (movie != null) {
            db.insert(TABLE_MOVIES, null, movieValues);
        } else {
            Logging.log("Movie is Null");
        }
        db.close();
    }

    // get All Movies for Favourites view
    public MoviesList getAllMovies() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_MOVIES, null);

        MoviesList movies = new MoviesList();

        while (c.moveToNext()) {
            Movie movie = new Movie();
            movie.setId(c.getInt(0));
            movie.setTitle(c.getString(1));
            movie.setOverview(c.getString(4));
            movie.setPoster_path(c.getString(2));
            movie.setRelease_date(c.getString(3));
            movie.setVote_average(c.getString(5));
            movies.add(movie);
        }
        c.close();
        db.close();
        return movies;
    }

    public Movie getFavoriteMovieData(String movieId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_MOVIES, new String[]{MOVIE_ID}, MOVIE_ID + "=?",
                new String[]{movieId}, null, null, null);

        Movie movie = new Movie();
        movie.setId(c.getInt(0));
        movie.setPoster_path(c.getString(2));
        movie.setVote_average(c.getString(5));
        movie.setRelease_date(c.getString(3));
        movie.setTitle(c.getString(1));
        movie.setOverview(c.getString(4));

        return movie;
    }

    //Search for a movie by Id, is it there or not
    public boolean findMovie(String movieId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_MOVIES, new String[]{MOVIE_ID}, MOVIE_ID + "=?",
                new String[]{movieId}, null, null, null);
        String _movieId = "";
        try {
            if (c != null && c.moveToFirst()) {
                _movieId = c.getString(c.getColumnIndex(MOVIE_ID));
            } else {
                return false;
            }

        } catch (SQLiteException ex) {
            Logging.log(ex.getMessage());
        } finally {
            c.close();
            db.close();
        }

        if (_movieId.equals(movieId)) {
            return true;
        } else {
            return false;
        }
    }
}
