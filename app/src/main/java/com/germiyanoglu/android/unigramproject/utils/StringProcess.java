package com.germiyanoglu.android.unigramproject.utils;

// TODO : 181 ) Defining String Process for username
public class StringProcess {

    public static String convertDottoSpaceUsername(String username){
        return username.replace(".", " ");
    }

    public static String convertSpacetoDotUsername(String username){
        return username.replace(" " , ".");
    }



}
