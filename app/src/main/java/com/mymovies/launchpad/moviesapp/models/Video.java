package com.mymovies.launchpad.moviesapp.models;

import com.mymovies.launchpad.moviesapp.utilities.Logging;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dell on 05/11/2016.
 */

public class Video {

    private String id;
    private String key;
    private String name;

    public Video(JSONObject jsonObject) {
        try {
            if(jsonObject.has("id")){
                id = jsonObject.getString("id");
            }

            if(jsonObject.has("key")){
                key = jsonObject.getString("key");
            }

            if(jsonObject.has("name")){
                name = jsonObject.getString("name");
            }
        }catch (JSONException ex){
            Logging.log("JSONException Video: "+ ex.getMessage());
        }

    }


    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
