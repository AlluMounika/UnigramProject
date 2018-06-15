package com.germiyanoglu.android.unigramproject.share;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.germiyanoglu.android.unigramproject.R;

import butterknife.ButterKnife;

// TODO : 305 ) Defining GalleryFragment class to determine fragment_gallery.xml
public class GalleryFragment extends Fragment {

    private static final String TAG = PhotoFragment.class.getName();


    // TODO : 306 ) Inflating fragment_gallery.xml
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        Log.d(TAG, "onCreateView: started.");
        ButterKnife.bind(this,view);

        return view;
    }
}
