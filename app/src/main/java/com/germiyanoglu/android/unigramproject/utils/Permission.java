package com.germiyanoglu.android.unigramproject.utils;

import android.Manifest;

// TODO : 287 ) Defining Permision class for Share picture via giving permission to open camera, get image and put image in the view
public class Permission {

    // TODO : 288 ) Defining manifest permision
    private static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final String CAMERA = Manifest.permission.CAMERA;

    // TODO : 289 ) Defining etter methods

    public static String getWriteExternalStorage() {
        return WRITE_EXTERNAL_STORAGE;
    }

    public static String getReadExternalStorage() {
        return READ_EXTERNAL_STORAGE;
    }

    public static String getCAMERA() {
        return CAMERA;
    }



}
