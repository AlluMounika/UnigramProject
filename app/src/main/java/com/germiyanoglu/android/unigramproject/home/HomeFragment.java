package com.germiyanoglu.android.unigramproject.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.germiyanoglu.android.unigramproject.R;

// TODO 55 ) Creating Home Fragment class to integrate fragment_home.xml
public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getName();


    // TODO 56 ) Inflating fragment_home.xml
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView is working");
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        return view;

    }
}
