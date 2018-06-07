package com.germiyanoglu.android.unigramproject.bottomnavigationbaractivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;

import com.germiyanoglu.android.unigramproject.R;
import com.germiyanoglu.android.unigramproject.bottomnavigationbar.BottomNavigationBarAnimation;
import com.germiyanoglu.android.unigramproject.settings.SettingsActivity;
import com.germiyanoglu.android.unigramproject.utils.AsyncTaskLoadImage;
import com.germiyanoglu.android.unigramproject.utils.PostAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

// TODO 33 ) Creating Share Activity
public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = ProfileActivity.class.getName();
    private static final int ICON_NUMBER_MENU = 0;

    @BindView(R.id.bottomNavigationView)
    BottomNavigationViewEx bottomNavigationViewEx;

    @BindView(R.id.profile_top_bar_toolbar)
    Toolbar toolbar;

    @BindView(R.id.profile_information_section_profile_image)
    CircleImageView profileImage;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.profile_information_section_image_recyleview)
    RecyclerView recyclerView;

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

        // TODO 119 ) Calling putProfileImageCircleImageView
        putProfileImageCircleImageView();

        // TODO 129 ) Calling sampleGridViewPost
        sampleGridViewPost();

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
        /*moreIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"moreIcon's setOnClickListener is working");
                Intent intent = new Intent(ProfileActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });*/


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


    public void openSettings(View view){
        Log.d(TAG,"moreIcon's setOnClickListener is working");
        Intent intent = new Intent(ProfileActivity.this,SettingsActivity.class);
        startActivity(intent);
    }

    // TODO 118 ) Putting ProfileImage in CircleImageView
    private void putProfileImageCircleImageView(){
        Log.d(TAG,"setCircleImageView is working");
        String imgUrl = "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png";
        new AsyncTaskLoadImage(profileImage).execute(imgUrl);
    }

    // TODO : 127 ) Adding sample imageurl in the arraylist
    private void sampleGridViewPost() {

        ArrayList<String> imageUrl = new ArrayList<>();
        imageUrl.add("http://www.gstatic.com/webp/gallery/1.jpg");
        imageUrl.add("http://www.gstatic.com/webp/gallery/2.jpg");
        imageUrl.add("http://www.gstatic.com/webp/gallery/3.jpg");
        imageUrl.add("http://www.gstatic.com/webp/gallery/4.jpg");
        imageUrl.add("http://www.gstatic.com/webp/gallery/5.jpg");
        imageUrl.add("http://www.gstatic.com/webp/gallery/1.jpg");
        imageUrl.add("http://www.gstatic.com/webp/gallery/2.jpg");
        imageUrl.add("http://www.gstatic.com/webp/gallery/3.jpg");
        imageUrl.add("http://www.gstatic.com/webp/gallery/4.jpg");
        imageUrl.add("http://www.gstatic.com/webp/gallery/5.jpg");

        setImageGridView(imageUrl);
    }

    // TODO : 128 ) Setting PostAdapter in GridView to display images in gridview
    private void setImageGridView(ArrayList<String> imageUrl){

        int orientation = GridLayout.VERTICAL;
        int span = getResources().getInteger(R.integer.gridlayout_span);
        boolean reverseLayout = false;
        GridLayoutManager layoutManager = new GridLayoutManager(this, span, orientation, reverseLayout);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        PostAdapter adapter = new PostAdapter(this);
        recyclerView.setAdapter(adapter);
    }
}

