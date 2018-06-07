package com.germiyanoglu.android.unigramproject.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.germiyanoglu.android.unigramproject.R;
import com.germiyanoglu.android.unigramproject.bottomnavigationbar.BottomNavigationBarAnimation;
import com.germiyanoglu.android.unigramproject.utils.AsyncTaskLoadImage;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

//   TODO 106 )  Edit Profile Fragment
public class EditProfileFragment extends Fragment {

    private static final String TAG = EditProfileFragment.class.getName();

    @BindView(R.id.edit_profile_information_section_profile_image)
    CircleImageView circleImageView;

    @BindView(R.id.edit_profile_top_bar_back_arrow)
    ImageView profileImage;


    //   TODO 107 )  Inflate fragment_edit_profile.xml
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreateView is working");
        View view = inflater.inflate(R.layout.fragment_edit_profile,container,false);
        ButterKnife.bind(this,view);

        // TODO 111 ) Calling setCircleImageView
        setCircleImageView();

        //  TODO 116 ) Gettting Back Settings Activity
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"backArrowImageView onClick is pressing");
                startActivity(new Intent(getActivity(),SettingsActivity.class));
            }
        });

        return view;
    }

    //   TODO 108 )  Setting image in CircleImageView
    private void setCircleImageView(){
        Log.d(TAG,"setCircleImageView is working");
        String imgUrl = "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png";
        new AsyncTaskLoadImage(circleImageView).execute(imgUrl);
    }

    /*  TOD 116 ) Gettting Back Settings Activity
    public void getBackSettings(View view){
        Log.d(TAG,"backArrowImageView onClick is pressing");
        getActivity();
    }*/


}
