package com.germiyanoglu.android.unigramproject.utils;

import java.io.File;
import java.util.ArrayList;

// TODO : 321 ) Getting paths from FilePath class in terms of directories and files including directory
public class GetFilePath {

    private static ArrayList<String> pathArray = null;


    // TODO : 322 ) Returning directories inside directory
    public static ArrayList<String> getDirectoryPaths(String directory){
        pathArray = new ArrayList<>();
        File file = new File(directory);
        File[] listOfFiles = file.listFiles();

        for(File selectedFile : listOfFiles){
            if(selectedFile.isDirectory()){
                pathArray.add(selectedFile.getAbsolutePath());
            }
        }

        return pathArray;
    }

    // TODO : 323 ) Returning file paths inside directory
    public static ArrayList<String> getFilePaths(String directory){
        pathArray = new ArrayList<>();
        File file = new File(directory);
        File[] listOfFiles = file.listFiles();

        for(File selectedFile : listOfFiles){
            if(selectedFile.isDirectory()){
                pathArray.add(selectedFile.getAbsolutePath());
            }
        }

        return pathArray;
    }



}
