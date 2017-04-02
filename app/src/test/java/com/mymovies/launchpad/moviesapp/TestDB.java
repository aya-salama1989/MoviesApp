package com.mymovies.launchpad.moviesapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.mymovies.launchpad.moviesapp.database.MoviesDBHelper;

import java.util.HashSet;

import static com.mymovies.launchpad.moviesapp.database.MoviesContract.MovieEntry.MOVIE_ID;
import static com.mymovies.launchpad.moviesapp.database.MoviesContract.MovieEntry.MOVIE_OVER_VIEW;
import static com.mymovies.launchpad.moviesapp.database.MoviesContract.MovieEntry.MOVIE_POSTER;
import static com.mymovies.launchpad.moviesapp.database.MoviesContract.MovieEntry.MOVIE_RATING;
import static com.mymovies.launchpad.moviesapp.database.MoviesContract.MovieEntry.MOVIE_RELEASE_DATE;
import static com.mymovies.launchpad.moviesapp.database.MoviesContract.MovieEntry.MOVIE_TITLE;
import static com.mymovies.launchpad.moviesapp.database.MoviesContract.MovieEntry.TABLE_MOVIES;
import static com.mymovies.launchpad.moviesapp.database.MoviesDBHelper.DATABASE_NAME;
import static com.mymovies.launchpad.moviesapp.database.MoviesDBHelper.getDataBaseInstance;

/**
 * Created by Dell on 12/11/2016.
 */

public class TestDB extends AndroidTestCase {

    public void deleteTheDatabase() {
        mContext.deleteDatabase(DATABASE_NAME);
    }

    MoviesDBHelper mDB;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // runs before each test case
        if (mDB != null) {
            deleteTheDatabase();
        } else {
            mDB = getDataBaseInstance(mContext);
        }

    }


    public void testDBCreation() throws Throwable {
        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(TABLE_MOVIES);

//        mContext.deleteDatabase(DATABASE_NAME);

        assertEquals(true, mDB.getWritableDatabase().isOpen());

        // have we created the tables we want?
        Cursor c = mDB.getWritableDatabase().rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(c.getString(0));
        } while (c.moveToNext());

        // if this fails, it means that your database doesn't contain both the location entry
        // and weather entry tables
        assertTrue("Error: Your database was created without both the location entry and weather entry tables",
                tableNameHashSet.isEmpty());

        // now, do our tables contain the correct columns?
        c = mDB.getWritableDatabase().rawQuery("PRAGMA table_info(" + TABLE_MOVIES + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> locationColumnHashSet = new HashSet<String>();
        locationColumnHashSet.add(MOVIE_ID);
        locationColumnHashSet.add(MOVIE_TITLE);
        locationColumnHashSet.add(MOVIE_RATING);
        locationColumnHashSet.add(MOVIE_POSTER);
        locationColumnHashSet.add(MOVIE_OVER_VIEW);
        locationColumnHashSet.add(MOVIE_RELEASE_DATE);


        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            locationColumnHashSet.remove(columnName);
        } while (c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                locationColumnHashSet.isEmpty());
        mDB.getWritableDatabase().close();

    }


    @Override
    protected void tearDown() throws Exception {

        mDB.close();

        super.tearDown();
        // runs after each test case
    }
}
