package com.germiyanoglu.android.unigramproject.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.germiyanoglu.android.unigramproject.R;
import com.germiyanoglu.android.unigramproject.login.LoginActivity;

import butterknife.ButterKnife;

// TODO : 134 ) Creating Register Activity
public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d(TAG,"onCreate is working");
        ButterKnife.bind(this);
    }
}
