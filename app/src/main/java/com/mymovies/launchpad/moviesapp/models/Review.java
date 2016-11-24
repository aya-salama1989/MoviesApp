package com.mymovies.launchpad.moviesapp.models;

import com.mymovies.launchpad.moviesapp.utilities.Logging;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dell on 05/11/2016.
 */

public class Review {

    private String id;
    private String author;
    private String content;
    private String url;

    public Review(JSONObject jsonObject) {
        try {
            if (jsonObject.has("id")) {
                id = jsonObject.getString("id");
            }

            if (jsonObject.has("author")) {
                author = jsonObject.getString("author");
            }

            if (jsonObject.has("content")) {
                content = jsonObject.getString("content");
            }

            if (jsonObject.has("id")) {
                url = jsonObject.getString("url");
            }


        } catch (JSONException ex) {
            Logging.log("Revies JSONEXCEPTTION: " + ex.getMessage());
        }
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }
}
