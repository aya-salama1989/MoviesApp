package com.mymovies.launchpad.moviesapp.models;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hp on 9/27/2016.
 */

public class Movie implements Parcelable {

    private String poster_path;
    private String overview;
    private String release_date;
    private int id;
    private String title;
    private String vote_average;

    protected Movie(Parcel in) {
        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        id = in.readInt();
        title = in.readString();
        vote_average = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public Movie(JSONObject jObj) {
        try {
            if (jObj.has("backdrop_path")) {
                poster_path = jObj.getString("backdrop_path");
            }

            if (jObj.has("overview")) {
                overview = jObj.getString("overview");
            }
            if (jObj.has("release_date")) {
                release_date = jObj.getString("release_date");
            }
            if (jObj.has("title")) {
                title = jObj.getString("title");
            }

            if (jObj.has("id")) {
                id = jObj.getInt("id");
            }

            if (jObj.has("vote_average")) {
                vote_average = jObj.getString("vote_average");
            }

        } catch (JSONException ex) {
            ex.getMessage();
        }

    }

    public Movie() {

    }



    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getVote_average() {
        return vote_average;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(vote_average);
    }
}
