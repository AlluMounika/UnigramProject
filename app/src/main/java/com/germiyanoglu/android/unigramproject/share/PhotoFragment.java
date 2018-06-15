package com.germiyanoglu.android.unigramproject.share;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.germiyanoglu.android.unigramproject.R;
import com.germiyanoglu.android.unigramproject.utils.Permission;

import butterknife.BindView;
import butterknife.ButterKnife;

// TODO : 297 ) Defining PhotoFragment class to determine fragment_photo.xml
public class PhotoFragment extends Fragment {

    private static final String TAG = PhotoFragment.class.getName();
    private static final int PHOTO_FRAGMENT_NUMBER = 1;
    private static final int CAMERA_REQUEST_CODE = 100;

    @BindView(R.id.openCamera_button)
    Button cemareBtn;

    // TODO : 304 ) Inflating fragment_photo.xml
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        Log.d(TAG, "onCreateView: started.");
        ButterKnife.bind(this,view);

        // TODO : 307 ) Opening Camera by pressing button
        cemareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Camera is laughing.");

                // TODO : 309 ) Checking whether the fragment opens the camera or not, If not, getting back photo fragement
                if(((ShareActivity)getActivity()).getCurrentTabNumber() == PHOTO_FRAGMENT_NUMBER){

                    // TODO : 310 ) Checking whether camera permission is convenient or not.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if(((ShareActivity)getActivity())
                                .checkSelfPermission(Permission.getCAMERA()) != PackageManager.PERMISSION_GRANTED
                                || ((ShareActivity)getActivity())
                                .checkSelfPermission(Permission.getCAMERA()) == PackageManager.PERMISSION_GRANTED){

                            Log.d(TAG, "onClick: Camera is starting");
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent,CAMERA_REQUEST_CODE);

                        }else{
                            // TODO : 312 ) Getting back SharedActivity without giving any permission
                            Intent intent = new Intent(getActivity(), ShareActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }

                }
            }
        });

        return view;
    }

    // TODO : 311 ) Defining onActionResult to retrieve image captured from camera
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST_CODE){
            Log.d(TAG, "onActivityResult: Photo is captured");
            Log.d(TAG, "onActivityResult: Returning photo Fragment");


        }
    }
}
