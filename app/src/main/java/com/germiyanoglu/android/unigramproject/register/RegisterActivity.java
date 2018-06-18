package com.germiyanoglu.android.unigramproject.register;

import android.content.Context;
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

import com.germiyanoglu.android.unigramproject.R;
import com.germiyanoglu.android.unigramproject.login.LoginActivity;
import com.germiyanoglu.android.unigramproject.modal.User;
import com.germiyanoglu.android.unigramproject.modal.UserAccount;
import com.germiyanoglu.android.unigramproject.utils.Methods;
import com.germiyanoglu.android.unigramproject.utils.StringProcess;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

// TODO : 134 ) Creating Register Activity
public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getName();

    // TODO : 156 ) Adding Firebase Authentication and determining authentication statue as an AuthStateListener
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    // TODO : 175 ) Adding Firebase Database and Reference
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private Context mContext;

    // TODO : 159 ) Defining attributes of activity_register.xml via ButterKnife
    @BindView(R.id.register_screen_input_email)
    EditText emailEditText;

    @BindView(R.id.register_screen_input_username)
    EditText usernameEditText;

    @BindView(R.id.register_screen_input_password)
    EditText passwordEditText;

    @BindView(R.id.register_screen_register_button)
    AppCompatButton registerButton;

    @BindView(R.id.register_screen_progressBar)
    ProgressBar registerProgressBar;

    @BindView(R.id.register_screen_loading_Please_Wait_textview)
    TextView loadingtextView;

    private Methods fireabaseMethods;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d(TAG,"onCreate is working");
        ButterKnife.bind(this);
        mContext = RegisterActivity.this;
        fireabaseMethods = new Methods(mContext);

        // TODO : 162 ) Set Gone to view of registerProgressBar and loadingtextView
        setGone();

        // TODO : 163 ) Calling firebaseAuthSetting
        firebaseAuthSetting();

        // TODO : 172 ) Calling registerProcess
        registerProcess();
    }


    // TODO : 157 ) Defining a method for determining whether authentication is sign-in or signout
    private void firebaseAuthSetting(){
        Log.d(TAG, "firebaseAuthSetting : firebase authentication is working.");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    // TODO : 176 ) Checking whether the username is already used or not
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            String username = usernameEditText.getText().toString().trim();
                            String email = emailEditText.getText().toString().trim();

                            fireabaseMethods.checkIfUsernameExistInRegister(username,email);
                        }



                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    // TODO : 196 ) Finishing Register Activity and return back to login screen
                    finish();

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }



    // TODO : 158 ) Defining onStart and onStop methods for Firebase
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


    // TODO : 160 ) Set Visible of progessBar and TextView
    private void setVisible(){
        registerProgressBar.setVisibility(View.VISIBLE);
        loadingtextView.setVisibility(View.VISIBLE);
    }

    // TODO : 161 ) Set Gone of progessBar and TextView
    private void setGone(){
        registerProgressBar.setVisibility(View.GONE);
        loadingtextView.setVisibility(View.GONE);
    }

    // TODO : 164 ) Register Process
    private void registerProcess(){
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : 165 ) Getting information of each EditText
                String email = emailEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // TODO : 167 ) Calling to checkFields method to check whether the fields of each EditText
                if(checkFields(email, username, password)){
                    // TODO : 168 ) Set Visible to view of registerProgressBar and loadingtextView
                    setVisible();

                    // TODO : 169 ) Calling registerEmail
                    fireabaseMethods.registerEmail(email,username,password,registerProgressBar,loadingtextView);

                }
            }
        });
    }

    // TODO : 166 ) Checking whether the fields of each EditText
    private boolean checkFields(String email, String username, String password) {
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            Toast.makeText(mContext
                    , "You must fill out all of fields"
                    , Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }


}
