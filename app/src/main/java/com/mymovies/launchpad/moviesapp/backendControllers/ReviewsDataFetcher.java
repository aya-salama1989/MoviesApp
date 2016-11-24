package com.mymovies.launchpad.moviesapp.backendControllers;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mymovies.launchpad.moviesapp.R;
import com.mymovies.launchpad.moviesapp.models.Reviews;
import com.mymovies.launchpad.moviesapp.utilities.Logging;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dell on 05/11/2016.
 */

public class ReviewsDataFetcher extends BaseDataFetcher {

    private Reviews reviews;


    public ReviewsDataFetcher(Context context, BaseDataResponseListener mListenr) {
        super(context, mListenr);
    }

    public void getReviews(String movieId) {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String URL = BaseURL
                + movieId + mContext.getResources().getString(R.string.url_reviews)
                + mContext.getResources().getString(R.string.api_key);

        Logging.log("ReviewsDataFetcher: " + URL);
        JsonObjectRequest jsonArrObjReq = new JsonObjectRequest(URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            reviews = new Reviews(response.getJSONArray("results"));
                            Logging.log("ReviewsDataFetcher: " + reviews.toString());

                            ((ReviewsFetcherListener) mListener).onConnectionDone(reviews);
                        } catch (JSONException ex) {
                            Logging.log(ex.getMessage());
                        }
                    }
                }, this.errorListener);

        retryPolicy(jsonArrObjReq);
        queue.add(jsonArrObjReq);
    }


    public interface ReviewsFetcherListener extends BaseDataResponseListener {
        void onConnectionDone(Reviews reviews);
    }

}
