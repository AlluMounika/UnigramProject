package com.germiyanoglu.android.unigramproject.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.germiyanoglu.android.unigramproject.R;
import com.germiyanoglu.android.unigramproject.bottomnavigationbar.BottomNavigationBarAnimation;
import com.germiyanoglu.android.unigramproject.modal.User;
import com.germiyanoglu.android.unigramproject.modal.UserAccount;
import com.germiyanoglu.android.unigramproject.modal.UserInformation;
import com.germiyanoglu.android.unigramproject.settings.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

// TODO 211 ) Creating ProfileFragment
public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getName();

    private static final int ICON_NUMBER_MENU = 0;

    // TODO 218 ) Adding Firebase Authentication and determining authentication statue as an AuthStateListener
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    // TODO : 221 ) Adding Firebase Database and Reference
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;


    // TODO 215 ) Defining attributes for fragment_profile_xml
    @BindView(R.id.bottomNavigationView)
    BottomNavigationViewEx bottomNavigationViewEx;

    @BindView(R.id.profile_top_bar_toolbar)
    Toolbar toolbar;

    @BindView(R.id.profile_information_section_profile_image)
    CircleImageView profileImage;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.profile_information_section_image_recyleview)
    RecyclerView recyclerView;

    @BindView(R.id.profile_information_section_profile_name)
    TextView profileName;

    @BindView(R.id.profile_information_section_description)
    TextView profileDescription;

    @BindView(R.id.profile_information_section_website)
    TextView profileWebsite;

    @BindView(R.id.information_section_profile_post_number)
    TextView profilePostNumber;

    @BindView(R.id.information_section_profile_followers_number)
    TextView profileFollowers;

    @BindView(R.id.information_section_profile_following_number)
    TextView profileFollowings;

    @BindView(R.id.profile_top_bar_settings_icon)
    ImageView openSettings;


    // TODO 212 ) Inflating fragment_home.xml
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView is working");
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        // TODO 35 ) Calling bottomNavigationViewMenu
        bottomNavigationViewMenu();

        // TODO 216 ) Calling createToolBarProfileMenu
        createToolBarProfileMenu();

        // TODO : 228 ) Calling firebaseAuthSetting
        firebaseAuthSetting();

        return view;

    }


    // TODO 34 ) Creating Bottom Navigation View Menu
    private void bottomNavigationViewMenu() {
        Log.d(TAG, "bottomNavigationViewMenu is starting");
        BottomNavigationBarAnimation.setBottomNavigationBarAnimation(bottomNavigationViewEx);

        // TODO 46 ) Providing navigation process between icons in BottomNavigationBar
        BottomNavigationBarAnimation.navitageIcon(getActivity(), bottomNavigationViewEx);

        // TODO 47 ) When login_icon is clicked , login_icon must be active
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ICON_NUMBER_MENU);
        menuItem.setChecked(true);
    }


    //  TODO 70 ) Creating tool bar profile menu-->
    private void createToolBarProfileMenu() {

        //  TODO 71 ) Defining menu login_icon in Tool bar (Deleted)-->
        //  TODO 87 ) Deleting if part because of not using menu properties (Deleted)
        //  TODO 88 ) Add more login_icon as an intent functionality via setOnClickListener (Deleted)

        ((ProfileActivity) getActivity()).setSupportActionBar(toolbar);

        // TODO 217 ) Calling openSettings
        openSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to settings part.");
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                //getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

    }


    // TODO : 219 ) Defining a method for determining whether authentication is sign-in or signout
    private void firebaseAuthSetting() {
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
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        // TODO : 221 ) Retrieving user information from firebase database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // TODO : 222 ) Calling retrieveInformationFromFirebaseDatabase
                retrieveInformationFromFirebaseDatabase(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    // TODO : 220 ) Defining onStart and onStop methods for Firebase
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

    // TODO : 223 ) Retriving user information from fireabase database via getting methods from each child
    private UserInformation retrieveInformationFromFirebaseDatabase(DataSnapshot dataSnapshot) {
        Log.d(TAG, "retrieveInformationFromFirebaseDatabase: retrieving user acoount information from firebase.");

        // TODO : 224 ) Creating user and userAccount information
        UserAccount userAccount = new UserAccount();
        User user = new User();

        // TODO : 225 ) Getting UserID
        String userID = mAuth.getCurrentUser().getUid();

        // TODO : 226 ) Getting all information of user
        for (DataSnapshot ds : dataSnapshot.getChildren()) {


            // user_account node
            if (ds.getKey().equals(getActivity().getString(R.string.database_user_account_child_node))) {
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
            if (ds.getKey().equals(getActivity().getString(R.string.database_user_child_node))) {
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

    // TODO : 229 ) ---------------- K___A___L____D___I___M ----------------

}
