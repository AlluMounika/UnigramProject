package com.germiyanoglu.android.unigramproject.utils;

import android.util.Log;


// TODO : 181 ) Defining String Process for username
public class StringProcess {

    private static final String TAG = StringProcess.class.getName();

    public static String convertDottoSpaceUsername(String username){
        Log.d(TAG,"convertDottoSpaceUsername : " + username.replace(".", " "));
        return username.replace(".", " ");
    }

    public static String convertSpacetoDotUsername(String username){
        Log.d(TAG,"convertSpacetoDotUsername : " + username.replace(".", " "));
        return username.replace(" " , ".");
    }



}
