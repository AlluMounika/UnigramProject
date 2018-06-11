package com.germiyanoglu.android.unigramproject.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.germiyanoglu.android.unigramproject.R;
import com.germiyanoglu.android.unigramproject.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

//   TODO 110 ) Creating Signout Fragment
public class SignOutFragment extends Fragment {

    private static final String TAG = SignOutFragment.class.getName();

    // TODO : 196 ) Adding Firebase Authentication and determining authentication statue as an AuthStateListener
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    // TODO : 197 ) Defining attributes of fragment_signout.xml via ButterKnife
    @BindView(R.id.signout_progressBar)
    ProgressBar signoutProgressBar;

    @BindView(R.id.signout_textview)
    TextView signoutTextView;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreateView is working");
        View view = inflater.inflate(R.layout.fragment_signout,container,false);
        ButterKnife.bind(this,view);

        // TODO : 122 ) Hiding progessbar and textview
        setGone();

        // TODO : 206 ) Calling firebaseAuthSetting
        firebaseAuthSetting();

        // TODO : 200 ) Calling signoutProcess
        signoutProcess();

        return view;
    }

    // TODO : 201 ) Opening dialogInterface for signing out according to button's feature
    private void signoutProcess() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        // TODO : 202 ) Signout Process
                        setVisible();
                        mAuth.signOut();
                        getActivity().finish();

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you want to sign out?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    // TODO : 198 ) Set Visible of progessBar and TextView
    private void setVisible(){
        signoutProgressBar.setVisibility(View.VISIBLE);
        signoutTextView.setVisibility(View.VISIBLE);
    }

    // TODO : 199 ) Set Gone of progessBar and TextView
    private void setGone(){
        signoutProgressBar.setVisibility(View.GONE);
        signoutTextView.setVisibility(View.GONE);
    }


    // TODO : 203 ) Defining a method for determining whether authentication is sign-in or signout
    private void firebaseAuthSetting(){
        Log.d(TAG, "firebaseAuthSetting : firebase authentication is working.");

        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");

                    Log.d(TAG, "onAuthStateChanged: navigating back to login screen.");

                    // TODO : 205 ) After finishing activity , get back login activity
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    // TODO : 207 ) Disabling navigation back after signout
                    // While pressing back button after sign out, the app is closed.
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };
    }

    // TODO : 204 ) Defining onStart and onStop methods for Firebase
    @Override
    public void onStart() {
        Log.d(TAG, "onStart is working");
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop is working");
        super.onStop();
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

}
