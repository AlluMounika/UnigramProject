package com.germiyanoglu.android.unigramproject.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.germiyanoglu.android.unigramproject.R;
import com.germiyanoglu.android.unigramproject.bottomnavigationbar.BottomNavigationBarAnimation;
import com.germiyanoglu.android.unigramproject.modal.User;
import com.germiyanoglu.android.unigramproject.modal.UserAccount;
import com.germiyanoglu.android.unigramproject.modal.UserInformation;
import com.germiyanoglu.android.unigramproject.utils.AsyncTaskLoadImage;
import com.germiyanoglu.android.unigramproject.utils.ConfirmReauthenticatePassword;
import com.germiyanoglu.android.unigramproject.utils.Methods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

//   TODO 106 )  Edit Profile Fragment
public class EditProfileFragment extends Fragment
    implements ConfirmReauthenticatePassword.OnPasswordConfirmationListener{

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

    @BindView(R.id.edit_profile_top_bar_settings_icon)
    ImageView checkUpdate;

    private Methods firebaseMethods;
    private UserAccount userAccount;
    private User user;
    private UserInformation userInformationEditProfile;

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

        // TODO : 255 ) Confirming update process
        checkUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to update changes.");
                saveEditProfileInformation();
            }
        });

        // TODO 111 ) Calling firebaseAuthSetting
        firebaseAuthSetting();

        return view;
    }

    //   TODO 108 )  Setting image in CircleImageView (DELETED)

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
        userInformationEditProfile = userInformation;

        // TODO : 247 ) Getting user account information object from UserInformation object
        userAccount = userInformation.getAccountInformation();

        // TODO : 251 ) Getting user  object from UserInformation object
        user = userInformation.getUser();

        // TODO : 248 ) Getting information from UserAccount object
        String profilePhoto = userAccount.getProfile_photo();
        String fullName = userAccount.getUserfullname();
        String userName = userAccount.getUsername();
        String description = userAccount.getDescription();
        String website = userAccount.getWebsite();
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

    // TODO : 252 ) Saving edit profile information
    private void saveEditProfileInformation(){
        Log.d(TAG, "saveEditProfileInformation is working ");

        // TODO : 253 ) Getting all values from EditText
        final String fullName = editProfileFullName.getText().toString();
        final String userName = editProfileUsername.getText().toString();
        final String description = editProfileDescription.getText().toString();
        final String website = editProfileWebsite.getText().toString();
        final String email = editProfileEmail.getText().toString();
        final String phoneNumber = editProfilePhoneNumber.getText().toString();

        final String userID = mAuth.getCurrentUser().getUid();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange is working ");


                // TODO : 254 ) Changing username
                if(!userInformationEditProfile.getUser().getUsername().equals(userName)){
                    Log.d(TAG, "changing username");
                    checkUserName(userName);
                }

                // TODO : 260 ) Changing email
                if(!userInformationEditProfile.getUser().getUserEmail().equals(email)){
                    Log.d(TAG, "changing email");
                    // TODO : 264 ) Showing dialong screen
                    ConfirmReauthenticatePassword dialog = new ConfirmReauthenticatePassword();
                    // TODO : 271 ) Setting target fragment with requestCode(not important) because when dialog is closed,
                    // targetFragment which is named for EditProfileFragment opens
                    dialog.setTargetFragment(EditProfileFragment.this, 0);
                    dialog.show(getFragmentManager(), "ConfirmReauthenticatePassword");



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    // TODO : 255 ) Checking whether username already exists or not after editing process
    private void checkUserName(final String userName) {
        Log.d(TAG, "checkUserName: Checking if  " + userName + " already exists.");

        // TODO : 256 ) Creating firebase query to find username
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child(getString(R.string.database_user_child_node)).orderByChild("username").equalTo(userName);

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
                            Log.d(TAG, "onDataChange: Username found : " + issue.getValue(User.class).getUsername());
                            Toast.makeText(getActivity(), "That username already exists. Please change as a new one", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{ // TODO : 259 ) Adding new username
                    firebaseMethods.updateUsername(userName);
                    Toast.makeText(getActivity(), "Username changed.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    // TODO : 273 ) Defining override  passwordConfirmation method
    @Override
    public void passwordConfirmation(String password) {
        Log.d(TAG, "passwordConfirmation is working . Password : " + password);

        // TODO : 274 ) Updating Email Process
        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(mAuth.getCurrentUser().getEmail(), password);

        FirebaseUser user = mAuth.getCurrentUser();

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "User re-authenticated.");
                        if(task.isSuccessful()){
                            firebaseMethods.updateUserEmail(editProfileEmail.getText().toString());
                        } else {
                            // Password is incorrect
                            Log.d(TAG, "onComplete: re-authentication failed.");
                        }
                    }
                });
    }


}
