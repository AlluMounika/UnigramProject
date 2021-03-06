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

// TODO 120 ) Designing GridViewPostAdapter to display posts in GridView
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostAdapterViewHolder>{

    private static final String TAG = PostAdapter.class.getName();

    // TODO  121) Defining Context
    private Context mContext;

    // TODO  122 ) Defining Arraylist for image urls
    private ArrayList<String> photoList;

    //TODO : 301 ) Defining mOnClickHandler
    private final PostAdapterOnClickHandler mOnClickHandler;

    // TODO  123 ) Defining a constructor for ReviewAdapter
    public PostAdapter(Context context,PostAdapterOnClickHandler clickHandler,ArrayList<String> imglist ) {
        this.mContext = context;
        this.photoList = imglist;
        this.mOnClickHandler = clickHandler;
    }

    //TODO : 300 ) Defining onItemClickListener for Post(REQUIRED LATER)
    public interface PostAdapterOnClickHandler{
        void onClick(View v);
    }

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
    public void onBindViewHolder(@NonNull final PostAdapter.PostAdapterViewHolder holder, int position) {
        String imageUrl = photoList.get(position);
        System.out.println(TAG + " imageUrl is " + imageUrl);
        if(imageUrl==null){
            holder.progressBar.setVisibility(View.GONE);
        }else{
            holder.progressBar.setVisibility(View.VISIBLE);
            //Picasso.with(mContext).load(imageUrl).into(holder.postImage);

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
        if (photoList != null) {
            return photoList.size();
        } else {
            return 0;
        }
    }


    public class PostAdapterViewHolder  extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        // TODO  125 ) Defining progressBar and ImageView
        private ProgressBar progressBar;
        private ImageView postImage;

        public PostAdapterViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.gridview_progessBar);
            postImage = itemView.findViewById(R.id.gridview_post);
        }

        // TODO  126 ) Getting returns for each item
        public ProgressBar getProgressBar(){ return progressBar; }
        public ImageView getPostImage(){ return postImage; }


        @Override
        public void onClick(View v) {
            /*int adapterPosition = getAdapterPosition();
            String currentPhoto = photoList.get(adapterPosition);
            mOnClickHandler.onClick(currentPhoto);*/
        }
    }

    // TODO  124 ) Setting reviewData to listOfPhoto and save it
    public void setPostData(ArrayList<String> listOfPhoto) {
        photoList = listOfPhoto;
        notifyDataSetChanged();
    }
}
