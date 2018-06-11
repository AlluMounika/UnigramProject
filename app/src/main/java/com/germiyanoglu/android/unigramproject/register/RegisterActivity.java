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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d(TAG,"onCreate is working");
        ButterKnife.bind(this);
        mContext = RegisterActivity.this;

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


                            String username = usernameEditText.getText().toString();
                            String email = emailEditText.getText().toString();

                            // TODO : 177 ) Calling checkIfUsernameExist
                            // TODO : 182 ) Checking username
                            if(checkIfUsernameExist(username,dataSnapshot)){
                                // TODO : 183 ) push() generate random key
                                String append = myRef.push().getKey().substring(0,3);
                                Log.d(TAG, "onDataChange : username already exists. " +
                                        "Appending random value(key 0 to 3)" + append + "to " + username);
                                username += append;
                            }

                            // TODO : 191 ) Adding new user to firebase
                            addNewUser(email, username, "", "", "");

                            // TODO : 192 ) Defining Register Process completed
                            Toast.makeText(mContext, "Registration successful. Verifying email.",
                                    Toast.LENGTH_SHORT).show();

                            // TODO : 193 ) Signout process
                            mAuth.signOut();
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

    // TODO : 178 ) Calling checkIfUsernameExist
    private boolean checkIfUsernameExist(final String username, DataSnapshot dataSnapshot) {

        Log.d(TAG, "checkIfUsernameExists: Checking whether  " + username + " already exists or not .");

        // TODO : 179 ) Create User object
        User user = new User();

        String userID = mAuth.getCurrentUser().getUid();

        // TODO : 180 ) Comparing process
        for(DataSnapshot ds:dataSnapshot.child(userID).getChildren()){
            Log.d(TAG, "checkIfUsernameExists: dataSnapshot  " + ds);

            user.setUsername(ds.getValue(User.class).getUsername());
            Log.d(TAG, "checkIfUsernameExists: username  " + user.getUsername());

            // TODO : 181 ) Controlling username
            if(user.getUsername().equals(username)){
                Log.d(TAG, "checkIfUsernameExists: matching process found  " + user.getUsername());
                return true;
            }
        }

        return false;
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
                    registerEmail(email,username,password);
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

    // TODO : 170 ) Registering Information to Firebase
    private void registerEmail(String email, String username, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");

                            // TODO : 170 ) Getting userId
                            String userID = mAuth.getCurrentUser().getUid();

                            // TODO : 171 ) Showing Log including userId
                            Log.d(TAG, "onComplete: Authstate changed: " + userID);

                            // TODO : 173 ) Verifying Email
                            verifyEmail();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(mContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    // TODO : 174 ) Checking whether the email is verified or not
    private void verifyEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            user.sendEmailVerification()
                    .addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                            }else{
                                Toast.makeText(mContext,
                                        "No verification email.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


    // TODO : 184 ) Adding New User to Firebase
    public void addNewUser(String email, String username, String description, String website, String profile_photo){

        // TODO : 185 ) Getting userId
        String userID = mAuth.getCurrentUser().getUid();

        // TODO : 186 ) Creating user object
        User user = new User( userID,  StringProcess.convertSpacetoDotUsername(username),  email,  "1");

        // TODO : 187 ) Determining user child node and then setting user object under userId child node
        myRef.child(mContext.getString(R.string.database_user_child_node))
                .child(userID)
                .setValue(user);

        // TODO : 189 ) Creating userAccount for its user
        UserAccount userAccount = new UserAccount(
                userID,
                description,
                0,
                0,
                0,
                profile_photo,
                username,
                StringProcess.convertSpacetoDotUsername(username),
                website

        );

        // TODO : 190 ) Determining user account child node and then setting user account object under userId child node
        myRef.child(mContext.getString(R.string.database_user_account_child_node))
                .child(userID)
                .setValue(userAccount);

    }
}
