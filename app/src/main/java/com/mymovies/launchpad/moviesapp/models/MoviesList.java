package com.mymovies.launchpad.moviesapp.models;


import com.mymovies.launchpad.moviesapp.utilities.Logging;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by hp on 9/27/2016.
 */

public class MoviesList extends ArrayList<Movie> {

public MoviesList(){

}
    public MoviesList(JSONArray jArr) {

        for (int i = 0; i < jArr.length(); i++) {
            try {
                add(new Movie(jArr.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
