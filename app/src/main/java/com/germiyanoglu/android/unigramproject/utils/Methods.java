package com.germiyanoglu.android.unigramproject.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.germiyanoglu.android.unigramproject.R;
import com.germiyanoglu.android.unigramproject.modal.User;
import com.germiyanoglu.android.unigramproject.modal.UserAccount;
import com.germiyanoglu.android.unigramproject.modal.UserInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Methods {

    private static final String TAG = "FirebaseMethods";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private StorageReference mStorageReference;
    private String userID;

    //vars
    private Context mContext;
    private long mPhotoUploadProgress = 0;
    private String append;

    public Methods(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mContext = context;

        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
    }

    // TODO : 223 ) Retriving user information from fireabase database via getting methods from each child
    public UserInformation retrieveInformationFromFirebaseDatabase(DataSnapshot dataSnapshot) {
        Log.d(TAG, "retrieveInformationFromFirebaseDatabase: retrieving user acoount information from firebase.");

        // TODO : 224 ) Creating user and userAccount information
        UserAccount userAccount = new UserAccount();
        User user = new User();

        // TODO : 225 ) Getting UserID
        String userID = mAuth.getCurrentUser().getUid();

        // TODO : 226 ) Getting all information of user
        for (DataSnapshot ds : dataSnapshot.getChildren()) {


            // user_account node
            if (ds.getKey().equals(mContext.getString(R.string.database_user_account_child_node))) {
                Log.d(TAG, "retrieveInformationFromFirebaseDatabase: user_account: " + ds);

                try {

                    // userId
                    userAccount.setUserId(
                            ds.child(userID)
                                    .getValue(UserAccount.class)
                                    .getUserId()
                    );

                    // description
                    userAccount.setDescription(
                            ds.child(userID)
                                    .getValue(UserAccount.class)
                                    .getDescription()
                    );

                    // followers
                    userAccount.setFollowers(
                            ds.child(userID)
                                    .getValue(UserAccount.class)
                                    .getFollowers()
                    );

                    // following
                    userAccount.setFollowing(
                            ds.child(userID)
                                    .getValue(UserAccount.class)
                                    .getFollowing()
                    );

                    // posts
                    userAccount.setPosts(
                            ds.child(userID)
                                    .getValue(UserAccount.class)
                                    .getPosts()
                    );

                    //profile_photo
                    userAccount.setProfile_photo(
                            ds.child(userID)
                                    .getValue(UserAccount.class)
                                    .getProfile_photo()
                    );

                    // userfullname
                    userAccount.setUserfullname(
                            ds.child(userID)
                                    .getValue(UserAccount.class)
                                    .getUserfullname()
                    );

                    // username
                    userAccount.setUsername(
                            ds.child(userID)
                                    .getValue(UserAccount.class)
                                    .getUsername()
                    );

                    // website
                    userAccount.setWebsite(
                            ds.child(userID)
                                    .getValue(UserAccount.class)
                                    .getWebsite()
                    );

                    Log.d(TAG, "retrieveInformationFromFirebaseDatabase: user_account information: " + userAccount.toString());
                } catch (NullPointerException e) {
                    Log.e(TAG, "retrieveInformationFromFirebaseDatabase: NullPointerException: " + e.getMessage());
                }
            }

            // user t node
            if (ds.getKey().equals(mContext.getString(R.string.database_user_child_node))) {
                Log.d(TAG, "retrieveInformationFromFirebaseDatabase: user: " + ds);

                try {

                    // userId
                    user.setUserId(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getUserId()
                    );

                    // username
                    user.setUsername(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getUsername()
                    );

                    // userEmail
                    user.setUserEmail(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getUserEmail()
                    );

                    // userPhoneNumber
                    user.setUserPhoneNumber(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getUserPhoneNumber()
                    );

                    Log.d(TAG, "getUserAccountSettings: retrieved user information: " + user.toString());
                } catch (NullPointerException e) {
                    Log.e(TAG, "getUserAccountSettings: NullPointerException: " + e.getMessage());
                }
            }

        }

        // TODO : 227 ) Returning UserInformation object with user and user account information
        return new UserInformation(user, userAccount);
    }

    // TODO : 259 ) Updating UserName
    public void updateUsername(String username){
        Log.d(TAG, "updateUsername: Update: " + username);

        // TODO : 259 ) Updating UserName in both user node and user account node
        myRef.child(mContext.getString(R.string.database_user_child_node))
                .child(userID)
                .child("username")
                .setValue(username);

        myRef.child(mContext.getString(R.string.database_user_account_child_node))
                .child(userID)
                .child("username")
                .setValue(username);
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

    // TODO : 255 ) Checking whether username already exists or not after editing process
    public void checkIfUsernameExistInRegister(final String userName,final String email) {
        Log.d(TAG, "checkUserName: Checking if  " + userName + " already exists.");


        // TODO : 256 ) Creating firebase query to find username
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("user").orderByChild("username").equalTo(userName);

        // TODO : 257 ) Defining query listener
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "user" node with all children with username
                    // TODO : 258 ) Username already exists
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        // do something with the individual "user"
                        if (issue.exists()){
                            // TODO : 177 ) Logging checkIfUsernameExist
                            Log.d(TAG, "checkIfUsernameExists: Username found: " + issue.getValue(User.class).getUsername());
                            // TODO : 183 ) push() generate random key
                            append = myRef.push().getKey().substring(0,3);
                            Log.d(TAG, "onDataChange : username already exists. " +
                                    "Appending random value(key 0 to 3)" + append + "to " + userName);
                        }
                    }
                }

                // TODO : 182 ) Appending username with random value
                String mUsername = userName + append;

                // TODO : 191 ) Adding new user to firebase
                addNewUser(email, mUsername, "", "", "");

                // TODO : 192 ) Defining Register Process completed
                Toast.makeText(mContext, "Registration successful. Verifying email.",
                        Toast.LENGTH_SHORT).show();

                // TODO : 193 ) Signout process
                mAuth.signOut();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // TODO : 170 ) Registering Information to Firebase
    public void registerEmail(String email, String username, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
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

    // TODO : 275 ) Defining User Email to be updated
    public void updateUserEmail(final String email){
        Log.d(TAG, "updateUserEmail: Email: " + email);

        mAuth.fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                if(task.isSuccessful()){
                    ///////// getProviders().size() will return size 1. if email ID is available.
                    // TODO : 276 ) Showing that Email updated already exists
                    if(task.getResult().getProviders().size() == 1){
                        Log.d(TAG, "onComplete: Email already exists ");
                        Toast.makeText(mContext, "That email is already used. Please change it as a new one", Toast.LENGTH_SHORT).show();

                    }else{

                        // TODO : 277 ) Updating User Email
                        FirebaseUser user = mAuth.getCurrentUser();

                        user.updateEmail(email)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "User email address updated.");

                                            myRef.child(mContext.getString(R.string.database_user_child_node))
                                                    .child(userID)
                                                    .child("email")
                                                    .setValue(email);

                                            Toast.makeText(mContext, "User email updated", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                        });
                    }

                }


                // TODO : 278 )  --------------  K --- A --- L --- D --- I --- M ------------------


            }

        });
    }
}
