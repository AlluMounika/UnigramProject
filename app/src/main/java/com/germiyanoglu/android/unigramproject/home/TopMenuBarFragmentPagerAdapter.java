package com.germiyanoglu.android.unigramproject.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;

// TODO 60 ) Creating BottomNavigationBarFragmentPagerAdapter to store bar fragment
// while keeping the fragement of selected bar icon in the memory.
public class TopMenuBarFragmentPagerAdapter extends FragmentPagerAdapter{

    private static final String TAG = TopMenuBarFragmentPagerAdapter.class.getName();

    // TODO 61 ) Creating ArrayList for defining selected position of bar icon
    ArrayList<Fragment> barIconArrayList = new ArrayList<>();

    public TopMenuBarFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return barIconArrayList.get(position);
    }

    @Override
    public int getCount() {
        return barIconArrayList.size();
    }


    // TODO 62 ) Creating createFragement for adding top bar icon fragment (camera,home and message) in the arraylist
    public void createFragement(){

        Log.d(TAG,"createFragement is working");
        Fragment cameraFragment = new CameraFragment();
        Fragment homeFragment = new HomeFragment();
        Fragment messageFragment = new MessageFragment();

        barIconArrayList.add(cameraFragment);
        barIconArrayList.add(homeFragment);
        barIconArrayList.add(messageFragment);
    }


}
