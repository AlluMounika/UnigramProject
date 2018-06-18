package com.germiyanoglu.android.unigramproject.utils;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;

// TODO : 321 ) Getting paths from FilePath class in terms of directories and files including directory
public class GetFilePath {

    private static final String TAG = GetFilePath.class.getName();

    private static ArrayList<String> pathArray = null;


    // TODO : 322 ) Returning directories inside directory
    public static ArrayList<String> getDirectoryPaths(String directory){
        Log.d(TAG, " getDirectoryPaths is working with " + directory);
        pathArray = new ArrayList<>();
        File file = new File(directory);
        File[] listOfFiles = file.listFiles();
        Log.d(TAG, " getDirectoryPaths : the size of listOfFiles  " + listOfFiles.length);
        if(listOfFiles != null) {
            for (File selectedFile : listOfFiles) {
                if (selectedFile.isDirectory()) {
                    pathArray.add(selectedFile.getAbsolutePath());
                }
            }
        }

        Log.d(TAG, " getDirectoryPaths : " + pathArray);
        return pathArray;
    }

    // TODO : 323 ) Returning file paths inside directory
    public static ArrayList<String> getFilePaths(String directory){
        Log.d(TAG, " getFilePaths is working with" + directory);
        pathArray = new ArrayList<>();
        File file = new File(directory);
        File[] listOfFiles = file.listFiles();
        Log.d(TAG, " getFilePaths : the size of listOfFiles  " + listOfFiles.length);
        if(listOfFiles != null){
            for (File selectedFile : listOfFiles) {
                if (selectedFile.isFile()) {
                    pathArray.add(selectedFile.getAbsolutePath());
                }
            }
        }

        Log.d(TAG, " getFilePaths : " + pathArray);
        return pathArray;
    }



}
