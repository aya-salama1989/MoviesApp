package com.mymovies.launchpad.moviesapp.backendControllers;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mymovies.launchpad.moviesapp.R;
import com.mymovies.launchpad.moviesapp.utilities.Logging;


/**
 * Created by hp on 5/27/2016.
 */
public class BaseDataFetcher {
    protected String BaseURL;
    protected Context mContext;
    protected BaseDataResponseListener mListener;

    public BaseDataFetcher(Context context, BaseDataResponseListener mListener) {
        this.mContext = context;
        this.mListener = mListener;
        BaseURL = mContext.getResources().getString(R.string.url_main);
    }

    public Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
          //  TODO: put request url and set remove in error listener
            Logging.log("onErrorResponse :  " + volleyError.toString());
            mListener.onConnectionFailed();
        }
    };

    public void retryPolicy(JsonObjectRequest jsonObjReq){
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    public void retryPolicy(JsonArrayRequest jsonArrObjReq){
        jsonArrObjReq.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}
