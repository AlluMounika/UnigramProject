package com.germiyanoglu.android.unigramproject.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.germiyanoglu.android.unigramproject.R;
import com.germiyanoglu.android.unigramproject.bottomnavigationbar.BottomNavigationBarAnimation;
import com.germiyanoglu.android.unigramproject.utils.SettingsItemsPagerAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

//  TODO 89 ) Creating SettingsActivity
public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = SettingsActivity.class.getName();

    private static final int ICON_NUMBER_MENU = 4;

    @BindView(R.id.settings_content_items)
    ListView listView;

    @BindView(R.id.settings_top_bar_toolbar)
    Toolbar toolbar;

    private Context mContext;

    // TODO 113 ) Defining SettingsItemsPagerAdapter
    private SettingsItemsPagerAdapter settingsItemsPagerAdapter;

    // TODO 114 ) Defining ViewPager of settings layout
    @BindView(R.id.settings_fragment_item_viewpager_main)
    ViewPager viewPager;

    @BindView(R.id.settings_relativelayout)
    RelativeLayout relativeLayout;

    @BindView(R.id.bottomNavigationView)
    BottomNavigationViewEx bottomNavigationViewEx;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        Log.d(TAG,"onCreate is starting");
        mContext = SettingsActivity.this;

        //  TODO 91 ) Calling createSettingsItems
        createSettingsItems();

        //  TODO 92 ) Calling enableBackButton
        enableBackButton();

        //  TODO 114 ) Calling enableItemsFragement
        enableItemsFragement();

        // TODO 120 ) Calling bottomNavigationViewMenu
        bottomNavigationViewMenu();

        // TODO : 239 ) Calling getIntentFromProfileActivity
        getIntentFromProfileActivity();
    }


    //  TODO 90 ) Creating settings items
    private void createSettingsItems(){
        Log.d(TAG,"createSettingsItems is starting");

        ArrayList<String> settingsItem = new ArrayList<>();

        settingsItem.add("Edit Profile");
        settingsItem.add("Sign out");
        // More is coming

        ArrayAdapter adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, settingsItem);
        listView.setAdapter(adapter);

        //  TODO 115 ) Calling any item based on Fragment according to its position
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG,"Onclick is pressing and position number is " + position);
                setViewPager(position);
            }
        });
    }

    private void enableBackButton(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // TODO 112 ) Enabling items as a Fragement
    private void enableItemsFragement(){
        settingsItemsPagerAdapter = new SettingsItemsPagerAdapter(getSupportFragmentManager());
        settingsItemsPagerAdapter.addItemFragment();
    }

    // TODO 113 ) Designing ViewPager
    private void setViewPager(int fragmentNumber){
        relativeLayout.setVisibility(View.GONE);
        Log.d(TAG,"navigating fragments Number of fragment : " + fragmentNumber);
        viewPager.setAdapter(settingsItemsPagerAdapter);
        viewPager.setCurrentItem(fragmentNumber);
    }


    // TODO 117 ) Creating Bottom Navigation View Menu
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

    // TODO : 237 ) Getting intent with information from profile activity
    private void getIntentFromProfileActivity(){
        Intent intent = getIntent();

        if(intent.hasExtra("edit_profile")){
            Log.d(TAG, "getIntentFromProfileActivity: receiving from Profile Activity");
            setViewPager(determineIndexOfSettingsItemPagerAdapter());
        }

    }

    // TODO : 238 ) Determining index of Settings Item Pager Adapter according its arraylist position
    private int determineIndexOfSettingsItemPagerAdapter(){
        for (int i = 0; i < settingsItemsPagerAdapter.getItemFragmentArrayList().size(); i++)
        {
            Fragment object = settingsItemsPagerAdapter.getItemFragmentArrayList().get(i);
            if (object instanceof EditProfileFragment){
                Log.d(TAG, "determineIndexOfSettingsItemPagerAdapter: EditProfileFragment");
                return i;
            }else if(object instanceof SignOutFragment){
                Log.d(TAG, "determineIndexOfSettingsItemPagerAdapter: SignOutFragment");
                return i;
            }
        }
        return -1;

    }



}
