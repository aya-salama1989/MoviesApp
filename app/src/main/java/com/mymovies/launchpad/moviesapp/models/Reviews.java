package com.mymovies.launchpad.moviesapp.models;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.LinkedList;

/**
 * Created by Dell on 05/11/2016.
 */

public class Reviews extends LinkedList<Review>{

    public Reviews(JSONArray jArr) throws JSONException {
        for(int i = 0; i<jArr.length(); i++){
            add(new Review(jArr.getJSONObject(i)));
        }
    }

    public Reviews(){

    }

}
