package com.germiyanoglu.android.unigramproject.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.germiyanoglu.android.unigramproject.R;
import com.germiyanoglu.android.unigramproject.bottomnavigationbar.BottomNavigationBarAnimation;
import com.germiyanoglu.android.unigramproject.modal.User;
import com.germiyanoglu.android.unigramproject.modal.UserAccount;
import com.germiyanoglu.android.unigramproject.modal.UserInformation;
import com.germiyanoglu.android.unigramproject.utils.AsyncTaskLoadImage;
import com.germiyanoglu.android.unigramproject.utils.Methods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

//   TODO 106 )  Edit Profile Fragment
public class EditProfileFragment extends Fragment {

    private static final String TAG = EditProfileFragment.class.getName();

    @BindView(R.id.edit_profile_information_section_profile_image)
    CircleImageView circleImageView;

    @BindView(R.id.edit_profile_top_bar_back_arrow)
    ImageView profileImage;

    // TODO 243 ) Adding Firebase Authentication and determining authentication statue as an AuthStateListener
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    // TODO : 244 ) Adding Firebase Database and Reference
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    // TODO : 245 ) Defining attributes of edit_profile_information.xml
    @BindView(R.id.edit_profile_information_profile_username_information_name_edittext)
    EditText editProfileUsername;

    @BindView(R.id.edit_profile_information_profile_information_name_surname_edittext)
    EditText editProfileFullName;

    @BindView(R.id.edit_profile_information_profile_information_website_edittext)
    EditText editProfileWebsite;

    @BindView(R.id.edit_profile_information_profile_information_description_edittext)
    EditText editProfileDescription;

    @BindView(R.id.edit_profile_information_profile_information_email_edittext)
    EditText editProfileEmail;

    @BindView(R.id.edit_profile_information_profile_information_phonenumber_edittext)
    EditText editProfilePhoneNumber;

    private Methods firebaseMethods;

    //   TODO 107 )  Inflate fragment_edit_profile.xml
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreateView is working");
        View view = inflater.inflate(R.layout.fragment_edit_profile,container,false);
        ButterKnife.bind(this,view);
        firebaseMethods = new Methods(getActivity());

        //  TODO 116 ) Gettting Back Settings Activity
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"backArrowImageView onClick is pressing");
                startActivity(new Intent(getActivity(),SettingsActivity.class));
            }
        });

        // TODO 111 ) Calling firebaseAuthSetting
        firebaseAuthSetting();

        return view;
    }

    //   TODO 108 )  Setting image in CircleImageView
    private void setCircleImageView(){
        Log.d(TAG,"setCircleImageView is working");
        String imgUrl = "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png";
        new AsyncTaskLoadImage(circleImageView).execute(imgUrl);
    }

    // TODO : 240 ) Defining a method for determining whether authentication is sign-in or signout
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

        // TODO : 250 ) Retrieving user information from firebase database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                displayUserInformation(firebaseMethods.retrieveInformationFromFirebaseDatabase(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    // TODO : 242 ) Defining onStart and onStop methods for Firebase
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

    // TODO : 246 ) Displaying user information from user and user account
    private void displayUserInformation(UserInformation userInformation){
        Log.d(TAG, "displayUserInformation is working ");

        // TODO : 247 ) Getting user account information object from UserInformation object
        UserAccount displayUserInformation = userInformation.getAccountInformation();

        // TODO : 250 ) Getting user  object from UserInformation object
        User user = userInformation.getUser();

        // TODO : 248 ) Getting information from UserAccount object
        String profilePhoto = displayUserInformation.getProfile_photo();
        String fullName = displayUserInformation.getUserfullname();
        String userName = displayUserInformation.getUsername();
        String description = displayUserInformation.getDescription();
        String website = displayUserInformation.getWebsite();
        String email = user.getUserEmail();
        String phoneNumber = user.getUserPhoneNumber();


        // TODO : 249 ) Displaying information
        new AsyncTaskLoadImage(circleImageView).execute(profilePhoto);
        editProfileFullName.setText(fullName);
        editProfileUsername.setText(userName);
        editProfileDescription.setText(description);
        editProfileWebsite.setText(website);
        editProfileEmail.setText(email);
        editProfilePhoneNumber.setText(phoneNumber);

    }
}
