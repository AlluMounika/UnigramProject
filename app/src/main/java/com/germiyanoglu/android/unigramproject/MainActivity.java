package com.germiyanoglu.android.unigramproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.germiyanoglu.android.unigramproject.bottomnavigationbar.BottomNavigationBarAnimation;
import com.germiyanoglu.android.unigramproject.home.CameraFragment;
import com.germiyanoglu.android.unigramproject.home.TopMenuBarFragmentPagerAdapter;
import com.germiyanoglu.android.unigramproject.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final int ICON_NUMBER_MENU = 4;


    @BindView(R.id.bottomNavigationView)
    BottomNavigationViewEx bottomNavigationViewEx;

    @BindView(R.id.center_viewpager_main)
    ViewPager viewPager;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    // TODO : 135 ) Adding Firebase Authentication and determining authentication statue as an AuthStateListener
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Log.d(TAG,"onCreate is starting");

        // TODO 23 ) Calling bottomNavigationViewMenu
        bottomNavigationViewMenu();

        // TODO 64 ) Calling TopBarMenu
        topBarMenu(viewPager);

        // TODO : 137 ) Calling firebaseAuthSetting
        firebaseAuthSetting();



    }

    // TODO 20 ) Creating Bottom Navigation View Menu
    private void bottomNavigationViewMenu(){
        Log.d(TAG,"bottomNavigationViewMenu is starting");
        BottomNavigationBarAnimation.setBottomNavigationBarAnimation(bottomNavigationViewEx);

        // TODO 42 ) Providing navigation process between icons in BottomNavigationBar
        BottomNavigationBarAnimation.navitageIcon(this,bottomNavigationViewEx);

        // TODO 43 ) When login_icon is clicked , login_icon must be active
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ICON_NUMBER_MENU);
        menuItem.setChecked(true);

    }

    // TODO 63 ) Defining TopBarMenu to create top bar login_icon activity in the menu vias TopMenuBarFragmentPagerAdapter
    private void topBarMenu(ViewPager viewPager){
        TopMenuBarFragmentPagerAdapter topMenuBarFragmentPagerAdapter =
                new TopMenuBarFragmentPagerAdapter(getSupportFragmentManager());

        topMenuBarFragmentPagerAdapter.createFragement();

        viewPager.setAdapter(topMenuBarFragmentPagerAdapter);

        // TODO 65 ) Setting viewPager to TabLayout
        tabLayout.setupWithViewPager(viewPager);

        // TODO 67 ) Calling putIconsInTablayout
        putIconsInTablayout();
    }


    // TODO 66 ) Defining putIconsInTablayout to set icons in tablayout
    private void putIconsInTablayout(){

        final int CAMERA_POSITON = 0;
        final int HOME_POSITON = 1;
        final int MESSAGE_POSITON = 2;

        // TODO 66 ) Putting camera, home and message login_icon in tablayout
        tabLayout.getTabAt(CAMERA_POSITON).setIcon(R.drawable.ic_action_name);
        tabLayout.getTabAt(HOME_POSITON).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(MESSAGE_POSITON).setIcon(R.drawable.ic_arrow);
    }


    // TODO : 136 ) Defining a method for determining whether authentication is sign-in or signout
    private void firebaseAuthSetting(){
        Log.d(TAG, "firebaseAuthSetting : firebase authentication is working.");

        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                // TODO : 139 ) Calling checkCurrentUser
                checkCurrentUser(user);

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

    // TODO : 137 ) Defining onStart and onStop methods for Firebase
    @Override
    public void onStart() {
        Log.d(TAG, "onStart is working");
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
        checkCurrentUser(mAuth.getCurrentUser());
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop is working");
        super.onStop();
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    // TODO : 138 ) Checking whether the current user is or not, If it isn't, sending to LoginActivity
    private void checkCurrentUser(FirebaseUser user){
        Log.d(TAG, "checkCurrentUser: checking if user is logged in.");

        if(user == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
