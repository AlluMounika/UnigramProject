package com.germiyanoglu.android.unigramproject.bottomnavigationbar;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.germiyanoglu.android.unigramproject.MainActivity;
import com.germiyanoglu.android.unigramproject.R;
import com.germiyanoglu.android.unigramproject.likes.LikesActivity;
import com.germiyanoglu.android.unigramproject.profile.ProfileActivity;
import com.germiyanoglu.android.unigramproject.search.SearchActivity;
import com.germiyanoglu.android.unigramproject.share.ShareActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

// TODO 21 ) Creating BottomNavigationBarAnimation to set false of animation
public class BottomNavigationBarAnimation {

    private static final String TAG = BottomNavigationBarAnimation.class.getName();

    // TODO 22 ) Defining  setBottomNavigationBarAnimation to revoke animation of BottomNavigationBar
    public static void setBottomNavigationBarAnimation(BottomNavigationViewEx bottomNavigationBarAnimation){
        Log.d(TAG,"setBottomNavigationBarAnimation is working");
        bottomNavigationBarAnimation.enableAnimation(false);
        bottomNavigationBarAnimation.enableItemShiftingMode(false);
        bottomNavigationBarAnimation.enableShiftingMode(false);
    }

    // TODO 36 ) Defining navitageIcon to navigate icons while each of them is pressing
    public static void navitageIcon(final Context context,final BottomNavigationViewEx bottomNavigationBarAnimation){

        bottomNavigationBarAnimation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Log.d(TAG,"setOnNavigationItemSelectedListener is working");

                switch (item.getItemId()) {
                    case R.id.ic_profile:
                        openProfileActivity(context);  // ICON_NUMBER_MENU = 0;
                        break;
                    case R.id.ic_likes:
                        openLikeActivity(context);  // ICON_NUMBER_MENU = 1;
                        break;
                    case R.id.ic_add:
                        openAddActivity(context);  // ICON_NUMBER_MENU = 2;
                        break;
                    case R.id.ic_search:
                        openSearchActivity(context); // ICON_NUMBER_MENU = 3;
                        break;
                    case R.id.ic_home:
                        openHomeActivity(context); // ICON_NUMBER_MENU = 4;
                        break;

                    default:
                        return false;
                }

                return false;
            }
        });

    }


    // TODO 37 ) Opening openProfileActivity
    private static void openProfileActivity(final Context context){
        Log.d(TAG,"openProfileActivity is working");
        Intent intent = new Intent(context, ProfileActivity.class);
        context.startActivity(intent);
    }

    // TODO 38 ) Opening openLikeActivity
    private static void openLikeActivity(final Context context){
        Log.d(TAG,"openLikeActivity is working");
        Intent intent = new Intent(context, LikesActivity.class);
        context.startActivity(intent);
    }

    // TODO 39 ) Opening openShareActivity
    private static void openAddActivity(final Context context){
        Log.d(TAG,"openShareActivity is working");
        Intent intent = new Intent(context, ShareActivity.class);
        context.startActivity(intent);
    }

    // TODO 40 ) Opening openSearchActivity
    private static void openSearchActivity(final Context context){
        Log.d(TAG,"openSearchActivity is working");
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    // TODO 41 ) Opening openHomeActivity
    private static void openHomeActivity(final Context context){
        Log.d(TAG,"openHomeActivity is working");
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
