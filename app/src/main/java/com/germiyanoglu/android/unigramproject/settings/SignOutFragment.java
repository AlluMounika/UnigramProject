package com.germiyanoglu.android.unigramproject.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.germiyanoglu.android.unigramproject.R;

import butterknife.ButterKnife;

//   TODO 110 ) Creating Signout Fragment
public class SignOutFragment extends Fragment {

    private static final String TAG = SignOutFragment.class.getName();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreateView is working");
        View view = inflater.inflate(R.layout.fragment_signout,container,false);
        ButterKnife.bind(this,view);


        return view;
    }
}
