package com.germiyanoglu.android.unigramproject.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.germiyanoglu.android.unigramproject.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

// TODO : 330 ) Defining GalleryImageAdapter to show images inside its directory
public class GalleryImageAdapter extends RecyclerView.Adapter<GalleryImageAdapter.GalleryImageAdapterViewHolder>
    {

    private static final String TAG = PostAdapter.class.getName();

    // TODO  331 ) Defining Context
    private Context mContext;

    // TODO  332 ) Defining Arraylist for image urls
    private ArrayList<String> galleryImageList;

    // TODO  337 ) Defining append
    private String mAppend;

    private GalleryImageAdapterOnClickHandler mOnClickHandler;

    // TODO  338 ) Defining OnClickListener
    public interface GalleryImageAdapterOnClickHandler{
        void onClick(View view);
    }

    // TODO  333 ) Defining a constructor for ReviewAdapter
    public GalleryImageAdapter(Context context,ArrayList<String> imglist,String append) {
        this.mContext = context;
        if(context instanceof GalleryImageAdapter.GalleryImageAdapterOnClickHandler){
            mOnClickHandler = (GalleryImageAdapterOnClickHandler) context;
        }
        this.galleryImageList = imglist;
        this.mAppend = append;
    }


    @NonNull
    @Override
    public GalleryImageAdapter.GalleryImageAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.layout_postgridview;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new GalleryImageAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GalleryImageAdapter.GalleryImageAdapterViewHolder holder, int position) {
        String imageUrl = galleryImageList.get(position);
        imageUrl += mAppend;
        System.out.println(TAG + " imageUrl is " + imageUrl);
        if(imageUrl==null){
            holder.progressBar.setVisibility(View.GONE);
        }else{
            holder.progressBar.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(imageUrl).into(holder.postImage);

            Picasso.with(mContext)
                    .load(imageUrl)
                    .into(holder.postImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.progressBar.setVisibility(View.GONE);
                        }
                        @Override
                        public void onError() {

                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        if (galleryImageList != null) {
            return galleryImageList.size();
        } else {
            return 0;
        }
    }


    public class GalleryImageAdapterViewHolder extends RecyclerView.ViewHolder
        implements GalleryImageAdapterOnClickHandler{

        // TODO  334 ) Defining progressBar and ImageView
        private ProgressBar progressBar;
        private ImageView postImage;

        public GalleryImageAdapterViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.gridview_progessBar);
            postImage = itemView.findViewById(R.id.gridview_post);
        }

        // TODO  335 ) Getting returns for each item
        public ProgressBar getProgressBar(){ return progressBar; }
        public ImageView getPostImage(){ return postImage; }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String currentImage = galleryImageList.get(adapterPosition);
        }
    }

    // TODO  336 ) Setting reviewData to mTrailerList and save it
    public void setPostData(ArrayList<String> listOfPhoto) {
        galleryImageList = listOfPhoto;
        notifyDataSetChanged();
    }
}
