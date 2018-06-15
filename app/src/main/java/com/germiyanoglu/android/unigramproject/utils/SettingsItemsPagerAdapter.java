package com.germiyanoglu.android.unigramproject.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.germiyanoglu.android.unigramproject.settings.EditProfileFragment;
import com.germiyanoglu.android.unigramproject.settings.SignOutFragment;

import java.util.ArrayList;

//   TODO 109 )  Creating SettingsItemsPagerAdapter to add functionality for each item
public class SettingsItemsPagerAdapter extends FragmentPagerAdapter{

    private static final String TAG  = SettingsItemsPagerAdapter.class.getName();

    private final ArrayList<Fragment> itemFragmentArrayList = new ArrayList<>();


    public SettingsItemsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return itemFragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return itemFragmentArrayList.size();
    }

    public void addItemFragment(){

        Fragment editProfileFragement = new EditProfileFragment();
        Fragment signOutFragement = new SignOutFragment();

        itemFragmentArrayList.add(editProfileFragement);
        itemFragmentArrayList.add(signOutFragement);

    }

    public ArrayList<Fragment> getItemFragmentArrayList() {
        return itemFragmentArrayList;
    }


}
