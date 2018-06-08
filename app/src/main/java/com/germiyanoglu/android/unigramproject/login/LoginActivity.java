package com.germiyanoglu.android.unigramproject.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.germiyanoglu.android.unigramproject.R;

// TODO : 133 ) Creating Login Activity
public class LoginActivity extends AppCompatActivity{

    private static final String TAG = LoginActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG,"onCreate is working");
    }
}
