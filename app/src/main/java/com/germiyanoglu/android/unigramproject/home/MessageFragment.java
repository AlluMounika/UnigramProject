package com.germiyanoglu.android.unigramproject.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.germiyanoglu.android.unigramproject.R;

// TODO 59 ) Creating Camera Fragment class to integrate fragment_message.xml
public class MessageFragment extends Fragment {

    private static final String TAG = MessageFragment.class.getName();

    // TODO 60 ) Inflating fragment_message.xml
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView is working");
        View view = inflater.inflate(R.layout.fragment_message,container,false);

        return view;

    }
}
