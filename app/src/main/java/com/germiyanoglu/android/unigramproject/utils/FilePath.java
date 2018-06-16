package com.germiyanoglu.android.unigramproject.utils;

import android.os.Environment;

// TODO : 320 ) Defining file location paths
public class FilePath {

    // "storage/emulated/0"
    public String ROOT_DIR = Environment.getExternalStorageDirectory().getPath();

    public String PICTURES = ROOT_DIR + "/Pictures";
    public String CAMERA = ROOT_DIR + "/DCIM/camera";
    public String STORIES = ROOT_DIR + "/Stories";

    public String FIREBASE_STORY_STORAGE = "stories/user";
    public String FIREBASE_IMAGE_STORAGE = "photos/user/";
}
