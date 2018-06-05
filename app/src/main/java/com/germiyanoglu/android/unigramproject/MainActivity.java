package com.germiyanoglu.android.unigramproject;

import android.os.Bundle;
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

    }

    // TODO 20 ) Creating Bottom Navigation View Menu
    private void bottomNavigationViewMenu(){
        Log.d(TAG,"bottomNavigationViewMenu is starting");
        BottomNavigationBarAnimation.setBottomNavigationBarAnimation(bottomNavigationViewEx);

        // TODO 42 ) Providing navigation process between icons in BottomNavigationBar
        BottomNavigationBarAnimation.navitageIcon(this,bottomNavigationViewEx);

        // TODO 43 ) When icon is clicked , icon must be active
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ICON_NUMBER_MENU);
        menuItem.setChecked(true);

    }

    // TODO 63 ) Defining TopBarMenu to create top bar icon activity in the menu vias TopMenuBarFragmentPagerAdapter
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

        // TODO 66 ) Putting camera, home and message icon in tablayout
        tabLayout.getTabAt(CAMERA_POSITON).setIcon(R.drawable.ic_action_name);
        tabLayout.getTabAt(HOME_POSITON).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(MESSAGE_POSITON).setIcon(R.drawable.ic_arrow);
    }

}
