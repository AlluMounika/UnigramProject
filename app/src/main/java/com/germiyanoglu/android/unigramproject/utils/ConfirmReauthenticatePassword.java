package com.germiyanoglu.android.unigramproject.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.germiyanoglu.android.unigramproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

// TODO : 262 ) Defining ConfirmReauthenticatePassword for dialog screen
public class ConfirmReauthenticatePassword extends DialogFragment {

    private static final String TAG = ConfirmReauthenticatePassword.class.getName();

    // TODO : 263 ) Defining Attributes of Dialong Screen
    @BindView(R.id.dialog_confirm_button_Confirm)
    TextView yesTextView;

    @BindView(R.id.dialog_confirm_button_Cancel)
    TextView noTextView;

    @BindView(R.id.dialog_confirm_password_edittext)
    EditText passwordEdittext;


    // TODO : 268 ) defining inferface for calling method from EditProfileFragment with it's listener method
    public interface OnPasswordConfirmationListener{
        void passwordConfirmation(String password);
    }

    OnPasswordConfirmationListener mOnPasswordConfirmationListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView is working");
        View view = inflater.inflate(R.layout.confirm_password_dialog, container, false);
        ButterKnife.bind(this,view);
        // TODO : 265 ) Defining yes textview's  action
        yesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "yesTextView onClick is working");

                String password = passwordEdittext.getText().toString();
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getActivity(), "you must enter a password", Toast.LENGTH_SHORT).show();
                }else{
                    // TODO : 272 ) Calling passwordConfirmation
                    mOnPasswordConfirmationListener.passwordConfirmation(password);
                    getDialog().dismiss();
                }

            }
        });

        // TODO : 266 ) Defining no textview's  action
        noTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "noTextView onClick is working");
                // TODO : 267 ) Closing dialong screen
                getDialog().dismiss();
            }
        });

        return view;
    }

    // TODO : 269 ) Using onAttach method to attach listener to fragement bacause of solving null problem
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            // TODO : 270 ) Instead of using getActivty which it is sent to MainActivity and get it back to Edit Profile Frgament,
            // using getTargetFragment() for making a connection with Edit Profile Framgent and ConfirmReauthenticatePassword without accessing MainActivity
            mOnPasswordConfirmationListener  = (OnPasswordConfirmationListener) getTargetFragment();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }

    }



}
