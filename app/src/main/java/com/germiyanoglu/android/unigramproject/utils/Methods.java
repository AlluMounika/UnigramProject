package com.germiyanoglu.android.unigramproject.utils;

import android.content.Context;
import android.util.Log;

import com.germiyanoglu.android.unigramproject.R;
import com.germiyanoglu.android.unigramproject.modal.User;
import com.germiyanoglu.android.unigramproject.modal.UserAccount;
import com.germiyanoglu.android.unigramproject.modal.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
}
