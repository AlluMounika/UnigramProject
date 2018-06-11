package com.germiyanoglu.android.unigramproject.profile;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ProgressBar;

import com.germiyanoglu.android.unigramproject.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

// TODO 33 ) Creating Share Activity
public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = ProfileActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        //  TODO 71 ) Setting Tool bar (Deleted)-->

        Log.d(TAG,"onCreate is starting");

        // TODO 213 ) Calling Fragment Process
        fragmentProcess();



        //  TODO 90 ) Calling setGoneProgressBar
        //setGoneProgressBar();

        // TODO 119 ) Calling putProfileImageCircleImageView
        //putProfileImageCircleImageView();

        // TODO 129 ) Calling sampleGridViewPost
        //sampleGridViewPost();

    }

    // TODO 214 ) Determining layout as a fragment
    private void fragmentProcess() {
        Log.d(TAG,"fragmentProcess is working");
        ProfileFragment profileFragment = new ProfileFragment();
        FragmentTransaction transaction = ProfileActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.profile_container, profileFragment);
        transaction.addToBackStack("fragmentProcess");
        transaction.commit();
    }


//
//

//
//
//    //  TODO 72 ) Creating Menu Bar in Profile Activity-->
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.top_bar_profile_menu,menu);
//        return true;
//    }
//
//
//
//    //  TODO 89 ) Setting View as a Gone of progressBar
//    private void setGoneProgressBar(){
//        progressBar.setVisibility(View.GONE);
//    }
//
//

//
//    // TODO 118 ) Putting ProfileImage in CircleImageView
//    private void putProfileImageCircleImageView(){
//        Log.d(TAG,"setCircleImageView is working");
//        String imgUrl = "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png";
//        new AsyncTaskLoadImage(profileImage).execute(imgUrl);
//    }
//
//    // TODO : 127 ) Adding sample imageurl in the arraylist
//    private void sampleGridViewPost() {
//
//        ArrayList<String> imageUrl = new ArrayList<>();
//        imageUrl.add("http://www.gstatic.com/webp/gallery/1.jpg");
//        imageUrl.add("http://www.gstatic.com/webp/gallery/2.jpg");
//        imageUrl.add("http://www.gstatic.com/webp/gallery/3.jpg");
//        imageUrl.add("http://www.gstatic.com/webp/gallery/4.jpg");
//        imageUrl.add("http://www.gstatic.com/webp/gallery/5.jpg");
//        imageUrl.add("http://www.gstatic.com/webp/gallery/1.jpg");
//        imageUrl.add("http://www.gstatic.com/webp/gallery/2.jpg");
//        imageUrl.add("http://www.gstatic.com/webp/gallery/3.jpg");
//        imageUrl.add("http://www.gstatic.com/webp/gallery/4.jpg");
//        imageUrl.add("http://www.gstatic.com/webp/gallery/5.jpg");
//
//        setImageGridView(imageUrl);
//    }
//
//    // TODO : 128 ) Setting PostAdapter in GridView to display images in gridview
//    private void setImageGridView(ArrayList<String> imageUrl){
//
//        int orientation = GridLayout.VERTICAL;
//        int span = getResources().getInteger(R.integer.gridlayout_span);
//        boolean reverseLayout = false;
//        GridLayoutManager layoutManager = new GridLayoutManager(this, span, orientation, reverseLayout);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setHasFixedSize(true);
//
//        PostAdapter adapter = new PostAdapter(this,imageUrl);
//        recyclerView.setAdapter(adapter);
//    }
}

