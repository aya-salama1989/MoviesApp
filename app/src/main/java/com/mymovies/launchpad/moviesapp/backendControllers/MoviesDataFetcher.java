package com.mymovies.launchpad.moviesapp.backendControllers;


import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mymovies.launchpad.moviesapp.R;
import com.mymovies.launchpad.moviesapp.models.MoviesList;
import com.mymovies.launchpad.moviesapp.utilities.Logging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MoviesDataFetcher extends BaseDataFetcher {
    private MoviesList movies;

    public MoviesDataFetcher(Context context, BaseDataResponseListener mListenr) {
        super(context, mListenr);
    }

    public void search(String queryType) {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String URL = BaseURL
                + queryType
                + mContext.getResources().getString(R.string.api_key);

        Logging.log("MoviesDataFetcher: " + URL);
        JsonObjectRequest jsonArrObjReq = new JsonObjectRequest(URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Logging.log(response.toString());
                            JSONArray jsonArray = response.getJSONArray("results");
                            movies = new MoviesList(jsonArray);

                            ((DataFetcherListener) mListener).onConnectionDone(movies);

                        } catch (JSONException ex) {
                            ex.getMessage();
                        }

                    }

                }, this.errorListener);

        retryPolicy(jsonArrObjReq);
        queue.add(jsonArrObjReq);
    }


    public interface DataFetcherListener extends BaseDataResponseListener {
        void onConnectionDone(MoviesList movies);
    }


}

