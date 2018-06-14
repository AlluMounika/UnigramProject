package com.germiyanoglu.android.unigramproject.share;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.germiyanoglu.android.unigramproject.R;
import com.germiyanoglu.android.unigramproject.bottomnavigationbar.BottomNavigationBarAnimation;
import com.germiyanoglu.android.unigramproject.utils.Permission;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


import butterknife.BindView;
import butterknife.ButterKnife;

// TODO 27 ) Creating Add Activity
public class ShareActivity extends AppCompatActivity {

    private static final String TAG = ShareActivity.class.getName();
    private static final int ICON_NUMBER_MENU = 2;
    private static final int PERMISSIONS_CODE = 1;

    @BindView(R.id.bottomNavigationView)
    BottomNavigationViewEx bottomNavigationViewEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Log.d(TAG, "onCreate is starting");

        // TODO 29 ) Calling bottomNavigationViewMenu
        bottomNavigationViewMenu();

        // TODO : 291 ) Verifying Permissions via calling verifyPermissions method
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Permission.getWriteExternalStorage()) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Permission.getReadExternalStorage()) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Permission.getCAMERA()) != PackageManager.PERMISSION_GRANTED) {

                Log.d(TAG, "checkSelfPermission: Giving permission to "
                        + Permission.getWriteExternalStorage() + " "
                        + Permission.getReadExternalStorage() + " "
                        + Permission.getCAMERA() + " ");
                verifyPermissions(new String[]{Permission.getWriteExternalStorage(),
                        Permission.getReadExternalStorage(), Permission.getCAMERA()});

            } else {

            }

        }


    }

    // TODO 28 ) Creating Bottom Navigation View Menu
    private void bottomNavigationViewMenu(){
        Log.d(TAG, "bottomNavigationViewMenu is starting");
        BottomNavigationBarAnimation.setBottomNavigationBarAnimation(bottomNavigationViewEx);

        // TODO 50 ) Providing navigation process between icons in BottomNavigationBar
        BottomNavigationBarAnimation.navitageIcon(ShareActivity.this, bottomNavigationViewEx);

        // TODO 51 ) When login_icon is clicked , login_icon must be active
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ICON_NUMBER_MENU);
        menuItem.setChecked(true);

    }

    // TODO : 290 ) Verifying Permissions
    private void verifyPermissions(String[] strings) {
        Log.d(TAG, "verifyPermissions is working");

        ActivityCompat.requestPermissions(
                ShareActivity.this,
                strings,
                PERMISSIONS_CODE
        );
    }
}

