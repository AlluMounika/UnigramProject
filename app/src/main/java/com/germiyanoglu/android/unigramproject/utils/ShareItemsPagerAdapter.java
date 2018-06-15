package com.germiyanoglu.android.unigramproject.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.germiyanoglu.android.unigramproject.share.GalleryFragment;
import com.germiyanoglu.android.unigramproject.share.PhotoFragment;

import java.util.ArrayList;

// TODO : 296 )  Creating ShareItemsPagerAdapter to add functionality for each item to Share Activity
public class ShareItemsPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG  = ShareItemsPagerAdapter.class.getName();

    private final ArrayList<Fragment> shareItemFragmentArrayList = new ArrayList<>();

    public ShareItemsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return shareItemFragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return shareItemFragmentArrayList.size();
    }

    public void shareAddItemFragement(){
        Fragment galleryFragmentFragement = new GalleryFragment();
        Fragment photoFragmentFragement = new PhotoFragment();

        shareItemFragmentArrayList.add(galleryFragmentFragement);
        shareItemFragmentArrayList.add(photoFragmentFragement);
    }


}
