package com.mymovies.launchpad.moviesapp.utilities;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetConnectivity {

	public static boolean checkOnlineState(Context context) {
		ConnectivityManager CManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo NInfo = CManager.getActiveNetworkInfo();
		if (NInfo != null && NInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

}
