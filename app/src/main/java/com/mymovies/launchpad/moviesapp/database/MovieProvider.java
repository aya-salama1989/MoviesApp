package com.mymovies.launchpad.moviesapp.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.mymovies.launchpad.moviesapp.utilities.Logging;

import static com.mymovies.launchpad.moviesapp.database.MoviesContract.MovieEntry.MOVIE_ID;
import static com.mymovies.launchpad.moviesapp.database.MoviesContract.MovieEntry.TABLE_MOVIES;

/**
 * Created by Dell on 19/11/2016.
 */

public class MovieProvider extends ContentProvider {
    public final static int MOVIES = 100;
    public final static int MOVIE_BY_ID = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MoviesDBHelper db;


    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MoviesContract.CONTENT_AUTHORITY;

        // 1- A URI for All FavoritesActivity
        matcher.addURI(authority, MoviesContract.PATH_MOVIE, MOVIES);

        //2- A URI for each Movie data to show in details frag
        matcher.addURI(authority, MoviesContract.PATH_MOVIE + "/#", MOVIE_BY_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        db = MoviesDBHelper.getDataBaseInstance(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder) {
        Cursor retCursor;
        SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();
        qBuilder.setTables(TABLE_MOVIES);
        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                retCursor = db.getReadableDatabase().query(
                        MoviesContract.MovieEntry.TABLE_MOVIES,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                Logging.log("cursor size: " + retCursor.getCount());
                break;
            case MOVIE_BY_ID:
                String id = uri.getPathSegments().get(1);
                retCursor = db.getReadableDatabase().query(
                        MoviesContract.MovieEntry.TABLE_MOVIES,
                        projection,
                        MOVIE_ID + "=?",
                        new String[]{id},
                        null,
                        null,
                        sortOrder
                );

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return MoviesContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_BY_ID:
                return MoviesContract.MovieEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        Uri returnedUri;

        switch (match) {
            case MOVIES:
                long id = sqLiteDatabase.insert(TABLE_MOVIES, null, values);
                if (id > 0) {
                    returnedUri = ContentUris.withAppendedId(MoviesContract.MovieEntry.MOVIES_CONTENT_URI, id);
                } else {
                    throw new SQLiteException("Unsupported insert into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unsupported opertion");
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnedUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase writableDatabase = db.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Logging.log("URI: " + uri);
        Logging.log("match: " + match);
        int tasksDeleted;
        switch (match) {
            case MOVIE_BY_ID:
                String id = uri.getPathSegments().get(1);
                tasksDeleted = writableDatabase.delete(TABLE_MOVIES, MOVIE_ID + "=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (tasksDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return tasksDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
