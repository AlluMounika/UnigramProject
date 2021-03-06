package com.germiyanoglu.android.unigramproject.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.germiyanoglu.android.unigramproject.MainActivity;
import com.germiyanoglu.android.unigramproject.R;
import com.germiyanoglu.android.unigramproject.register.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

// TODO : 133 ) Creating Login Activity
public class LoginActivity extends AppCompatActivity{

    private static final String TAG = LoginActivity.class.getName();

    // TODO : 140 ) Adding Firebase Authentication and determining authentication statue as an AuthStateListener
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    // TODO : 141 ) Defining attributes of activity_login.xml via ButterKnife
    @BindView(R.id.login_screen_email)
    EditText emailEditText;

    @BindView(R.id.login_screen_password)
    EditText passwordEditText;

    @BindView(R.id.login_screen_login_button)
    AppCompatButton loginButton;

    @BindView(R.id.login_screen_progressBar)
    ProgressBar loginProgressBar;

    @BindView(R.id.login_screen_please_Wait_textview)
    TextView pleaseWaitTextView;

    @BindView(R.id.login_screen_signup_link)
    TextView registerlink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG,"onCreate is working");
        ButterKnife.bind(this);

        // TODO : 156 ) Calling navigateRegisterPage
        navigateRegisterPage();

        // TODO : 149 ) Calling setGone
        setGone();

        // TODO : 144 ) Calling firebaseAuthSetting
        firebaseAuthSetting();

        // TODO : 153 ) Calling loginProcess
        loginProcess();


    }


    // TODO : 142 ) Defining a method for determining whether authentication is sign-in or signout
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
                }
            }
        };
    }

    // TODO : 143 ) Defining onStart and onStop methods for Firebase
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


    // TODO : 145 ) Login Process
    private void loginProcess(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "login button onClick is working");

                // TODO : 146 ) Getting texts from email and password text
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this
                            , "You must fill out all of fields"
                            , Toast.LENGTH_SHORT).show();
                }else{

                    // TODO : 150 ) Showing progessbar and textview
                    setVisible();

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        //Toast.makeText(LoginActivity.this, "Authentication success.",
                                        //        Toast.LENGTH_SHORT).show();

                                        // TODO : 194 ) Navigating to MainActivity after user mail is verified
                                        if(user.isEmailVerified()){
                                            Log.d(TAG, "user email is verified");
                                            // TODO : 152 ) Hiding progessbar and textview
                                            setGone();

                                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                            startActivity(intent);
                                        }else{
                                            setGone();
                                            Toast.makeText(LoginActivity.this, "Please verify your email sending to you",
                                                    Toast.LENGTH_SHORT).show();

                                            // TODO : 195 ) Signout process
                                            mAuth.signOut();
                                        }

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, "User with this email already exist.",
                                                Toast.LENGTH_SHORT).show();

                                        // TODO : 151 ) Hiding progessbar and textview
                                        setGone();


                                    }

                                    // ...
                                }
                            });
                }


            }
        });


        // TODO : 155 ) If user is logged in, navigating to MainActivity
        if(mAuth.getCurrentUser() != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }


    // TODO : 147 ) Set Visible of progessBar and TextView
    private void setVisible(){
        loginProgressBar.setVisibility(View.VISIBLE);
        pleaseWaitTextView.setVisibility(View.VISIBLE);
    }

    // TODO : 148 ) Set Gone of progessBar and TextView
    private void setGone(){
        loginProgressBar.setVisibility(View.GONE);
        pleaseWaitTextView.setVisibility(View.GONE);
    }

    // TODO : 154 ) When user link create link which is named for "No account yet? Create a new one", navigating to RegisterActivity
    private void navigateRegisterPage(){
         registerlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to register screen");
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}

