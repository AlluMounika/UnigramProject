package com.germiyanoglu.android.unigramproject.bottomnavigationbaractivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.germiyanoglu.android.unigramproject.R;
import com.germiyanoglu.android.unigramproject.bottomnavigationbar.BottomNavigationBarAnimation;
import com.germiyanoglu.android.unigramproject.settings.SettingsActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;

// TODO 33 ) Creating Share Activity
public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = ProfileActivity.class.getName();
    private static final int ICON_NUMBER_MENU = 0;

    @BindView(R.id.bottomNavigationView)
    BottomNavigationViewEx bottomNavigationViewEx;

    @BindView(R.id.profile_top_bar_toolbar)
    Toolbar toolbar;

    @BindView(R.id.profile_top_bar_settings_icon)
    ImageView moreIcon;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        Log.d(TAG,"onCreate is starting");

        // TODO 35 ) Calling bottomNavigationViewMenu
        bottomNavigationViewMenu();

        //  TODO 90 ) Calling setGoneProgressBar
        setGoneProgressBar();


    }

    // TODO 34 ) Creating Bottom Navigation View Menu
    private void bottomNavigationViewMenu(){
        Log.d(TAG,"bottomNavigationViewMenu is starting");
        BottomNavigationBarAnimation.setBottomNavigationBarAnimation(bottomNavigationViewEx);

        // TODO 46 ) Providing navigation process between icons in BottomNavigationBar
        BottomNavigationBarAnimation.navitageIcon(ProfileActivity.this,bottomNavigationViewEx);

        // TODO 47 ) When icon is clicked , icon must be active
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ICON_NUMBER_MENU);
        menuItem.setChecked(true);
    }


    //  TODO 70 ) Creating tool bar profile menu-->
    private void createToolBarProfileMenu(){

        //  TODO 71 ) Setting Tool bar-->
        setSupportActionBar(toolbar);

        //  TODO 71 ) Defining menu icon in Tool bar-->
        //  TODO 87 ) Deleting if part because of not using menu properties
        //  TODO 88 ) Add more icon as an intent functionality via setOnClickListener
        moreIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"moreIcon's setOnClickListener is working");
                Intent intent = new Intent(ProfileActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });


    }


    //  TODO 72 ) Creating Menu Bar in Profile Activity-->
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_bar_profile_menu,menu);
        return true;
    }



    //  TODO 89 ) Setting View as a Gone of progressBar
    private void setGoneProgressBar(){
        progressBar.setVisibility(View.GONE);
    }

}

