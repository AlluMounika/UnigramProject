package com.germiyanoglu.android.unigramproject.share;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.germiyanoglu.android.unigramproject.R;
import com.germiyanoglu.android.unigramproject.utils.FilePath;
import com.germiyanoglu.android.unigramproject.utils.GalleryImageAdapter;
import com.germiyanoglu.android.unigramproject.utils.GetFilePath;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

// TODO : 305 ) Defining GalleryFragment class to determine fragment_gallery.xml
public class GalleryFragment extends Fragment {

    private static final String TAG = PhotoFragment.class.getName();

    // TODO : 313 ) Defining attributes of fragment_gallery.xml
    @BindView(R.id.gallery_fragment_galleryImageView)
    ImageView galleryImageView;

    @BindView(R.id.gallery_fragment_progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.gallery_gallerytoolbar_spinner)
    Spinner directorySpinner;

    @BindView(R.id.gallery_gallerytoolbar_close_imageview)
    ImageView closeGallery;

    @BindView(R.id.gallery_gallerytoolbar_next)
    TextView nextPart;

    @BindView(R.id.gallery_fragment_gallery_list_recyleview)
    RecyclerView recyclerView;

    // TODO : 314 ) Defining location as a directory for spinner
    private ArrayList<String> directories;
    private String mSelectedImage;



    // TODO : 306 ) Inflating fragment_gallery.xml
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        Log.d(TAG, "onCreateView: started.");
        ButterKnife.bind(this,view);
        directories = new ArrayList<>();
        setGoneProgressBar();
        // TODO : 315 ) Calling closeGalleryFragement
        closeGalleryFragement();

        // TODO : 317 ) Calling navigateNextScreen
        navigateNextScreen();

        // TODO : 325 ) Calling defineDirectoriesInSpinner
        defineDirectoriesInSpinner();

        return view;
    }



    // TODO : 316 ) Closing Gallery fragment by pressing the button
    private void closeGalleryFragement() {
        closeGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Gallery fragment is closing.");
                getActivity().finish();
            }
        });
    }

    // TODO : 318 ) Navigating nextScreen for shared image
    private void navigateNextScreen() {
        nextPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to the share screen with image");

                // navigating next part

                // Change profile image in settings activity
            }
        });

    }

    // TODO : 319 ) Defining directories from phone in spinner
    private void defineDirectoriesInSpinner(){
        Log.d(TAG, "defineDirectoriesInSpinner is working");
        FilePath filePaths = new FilePath();

        // pictures directory
        if (GetFilePath.getDirectoryPaths(filePaths.PICTURES) != null) {
            directories.addAll(GetFilePath.getDirectoryPaths(filePaths.PICTURES));
        }

        // camera directory
        directories.add(filePaths.CAMERA);


        Log.d(TAG, " defineDirectoriesInSpinner directories : " + directories.toString());

        // TODO : 323 ) Listing directories in spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, directories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        directorySpinner.setAdapter(adapter);


        // TODO : 324 ) Adding listener to list directories
        directorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: selected: " + directories.get(position));

                // TODO : 329 ) Calling listImages
                listImages(directories.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    // TODO : 326 ) Setting View as a Gone of progressBar
    private void setGoneProgressBar(){
        mProgressBar.setVisibility(View.GONE);
    }

    // TODO : 327 ) Setting View as a Visible of progressBar
    private void setVisibleProgressBar(){
        mProgressBar.setVisibility(View.GONE);
    }


    // TODO : 328 ) Listing images with respect to its directory
    private void listImages(String selectedDirectory){
        Log.d(TAG, "setupGridView: selected directory: " + selectedDirectory);
        final ArrayList<String> imgURLs = GetFilePath.getFilePaths(selectedDirectory);

        Log.d(TAG, "Directory imgURLs: " + imgURLs.toString());

        int orientation = GridLayout.VERTICAL;
        int span = getResources().getInteger(R.integer.gridlayout_span);
        boolean reverseLayout = false;
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), span, orientation, reverseLayout);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        GalleryImageAdapter galleryImageAdapter = new GalleryImageAdapter(getActivity(),imgURLs);
        recyclerView.setAdapter(galleryImageAdapter);
        Log.d(TAG, "galleryImageAdapter size: " + galleryImageAdapter.getItemCount());


        try{
            // TODO  337 ) Defining first element of array as a default to display it
            mSelectedImage = galleryImageAdapter.firstImageofRecyleView();
            setImage(mSelectedImage, galleryImageView);
        }catch (ArrayIndexOutOfBoundsException e){
            Log.e(TAG, "setupGridView: ArrayIndexOutOfBoundsException: " +e.getMessage() );
        }



    }

    // TODO  338 ) Dispalying default value in the image view
    private void setImage(String mSelectedImage, ImageView galleryImageView) {
        Log.d(TAG, "setImage: setting image in the imageview");
        String imageUrl = mSelectedImage;

        if(TextUtils.isEmpty(imageUrl)){
            setGoneProgressBar();
        }else{
            setVisibleProgressBar();

            Picasso.with(getActivity())
                    .load(imageUrl)
                    .into(galleryImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            setGoneProgressBar();
                        }
                        @Override
                        public void onError() {

                        }
                    });
        }
    }
}
