package com.mymovies.launchpad.moviesapp.backendControllers;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mymovies.launchpad.moviesapp.R;
import com.mymovies.launchpad.moviesapp.models.Videos;
import com.mymovies.launchpad.moviesapp.utilities.Logging;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dell on 05/11/2016.
 */

public class VideosDataFetcher extends BaseDataFetcher {

    private Videos videos;

    public VideosDataFetcher(Context context, BaseDataResponseListener mListenr) {
        super(context, mListenr);
    }

    public void getVideos(String movieId) {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String URL = BaseURL
                + movieId + mContext.getResources().getString(R.string.url_videos)
                + mContext.getResources().getString(R.string.api_key);

        Logging.log("VideosDataFetcher: " + URL);
        JsonObjectRequest jsonArrObjReq = new JsonObjectRequest(URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            videos = new Videos(response.getJSONArray("results"));
                            ((VideosDataFetcherListener) mListener).onConnectionDone(videos);

                        } catch (JSONException ex) {
                            Logging.log("VideosDataFetcher JSONException: " + ex.getMessage());
                        }
                    }

                }, this.errorListener);

        retryPolicy(jsonArrObjReq);
        queue.add(jsonArrObjReq);
    }


    public interface VideosDataFetcherListener extends BaseDataResponseListener {
        void onConnectionDone(Videos videos);
    }

}
