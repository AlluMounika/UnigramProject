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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

// TODO 120 ) Designing GridViewPostAdapter to display posts in GridView
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostAdapterViewHolder>{

    // TODO : 121) Defining Context
    private Context mContext;

    // TODO : 122 ) Defining Arraylist for image urls
    private ArrayList<String> imgUrls;

    // TODO : 123 ) Defining a constructor for ReviewAdapter
    public PostAdapter(Context context) {this.mContext = context;}

    @NonNull
    @Override
    public PostAdapter.PostAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.layout_postgridview;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new PostAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostAdapterViewHolder holder, int position) {
        String imageUrl = imgUrls.get(position);
        Picasso.with(mContext).load(imageUrl).into(holder.postImage);
        holder.progressBar.setVisibility(imageUrl==null ? View.VISIBLE : View.GONE );
    }

    @Override
    public int getItemCount() {
        if (imgUrls != null) {
            return imgUrls.size();
        } else {
            return 0;
        }
    }


    public class PostAdapterViewHolder  extends RecyclerView.ViewHolder{

        // TODO : 125 ) Defining progressBar and ImageView
        private ProgressBar progressBar;
        private ImageView postImage;

        public PostAdapterViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.gridview_progessBar);
            postImage = itemView.findViewById(R.id.gridview_post);
        }

        // TODO : 126 ) Getting returns for each item
        public ProgressBar getProgressBar(){ return progressBar; }
        public ImageView getPostImage(){ return postImage; }
    }

    // TODO : 124 ) Setting reviewData to mTrailerList and save it
    public void setPostData(ArrayList<String> imageUrls) {
        imgUrls = imageUrls;
        notifyDataSetChanged();
    }
}
