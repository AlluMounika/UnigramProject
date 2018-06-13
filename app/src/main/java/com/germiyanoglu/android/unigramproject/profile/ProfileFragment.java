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
import com.germiyanoglu.android.unigramproject.utils.AsyncTaskLoadImage;
import com.germiyanoglu.android.unigramproject.utils.Methods;
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

    @BindView(R.id.profile_top_bar_textview)
    TextView profileUsername;

    @BindView(R.id.information_section_profile_edit_profile)
    TextView editProfileTextView;

    private Methods firebaseMethods;

    // TODO 212 ) Inflating fragment_home.xml
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView is working");
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        firebaseMethods = new Methods(getActivity());

        // TODO 35 ) Calling bottomNavigationViewMenu
        bottomNavigationViewMenu();

        // TODO 216 ) Calling createToolBarProfileMenu
        createToolBarProfileMenu();

        // TODO : 228 ) Calling firebaseAuthSetting
        firebaseAuthSetting();

        // TODO : 236 ) Calling navigtateEditProfile
        navigtateEditProfile();

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
                // TODO : 234 ) Calling displayUserInformation within retrieveInformationFromFirebaseDatabase

                displayUserInformation(firebaseMethods.retrieveInformationFromFirebaseDatabase(dataSnapshot));
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

    // TODO : 229 ) Displaying user information from user and user account
    private void displayUserInformation(UserInformation userInformation){
        Log.d(TAG, "displayUserInformation is working ");

        // TODO : 230 ) Getting user account information object from UserInformation object
        UserAccount displayUserInformation = userInformation.getAccountInformation();

        // TODO : 231 ) Getting information from UserAccount object
        String profilePhoto = displayUserInformation.getProfile_photo();
        String fullName = displayUserInformation.getUserfullname();
        String userName = displayUserInformation.getUsername();
        String description = displayUserInformation.getDescription();
        String website = displayUserInformation.getWebsite();
        int followers = displayUserInformation.getFollowers();
        int following = displayUserInformation.getFollowing();
        int posts = displayUserInformation.getPosts();

        // TODO : 233 ) Hiding Progress Bar
        progressBar.setVisibility(View.GONE);

        // TODO : 232 ) Displaying information
        new AsyncTaskLoadImage(profileImage).execute(profilePhoto);
        profileName.setText(fullName);
        profileUsername.setText(userName);
        profileDescription.setText(description);
        profileWebsite.setText(website);
        profilePostNumber.setText(String.valueOf(posts));
        profileFollowers.setText(String.valueOf(followers));
        profileFollowings.setText(String.valueOf(following));

    }

    // TODO : 235 ) Navigating Edit Profile after pressing "Edit your Profile"
    private void navigtateEditProfile(){
        editProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "navigtateEditProfile is working ");
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                intent.putExtra("edit_profile", "Profile Activity");
                startActivity(intent);
                //getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
    }

}
