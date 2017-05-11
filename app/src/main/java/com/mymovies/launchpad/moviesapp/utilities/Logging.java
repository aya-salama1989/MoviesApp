package com.mymovies.launchpad.moviesapp.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


public class Logging{


	public static void log(String s){
		Log.d("TAG", s);
	}
	
	public static void Toast(Context context, String s){
		Toast.makeText(context, s,Toast.LENGTH_SHORT).show();
	}
}