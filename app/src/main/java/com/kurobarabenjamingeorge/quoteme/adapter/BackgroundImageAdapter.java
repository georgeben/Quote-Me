package com.kurobarabenjamingeorge.quoteme.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kurobarabenjamingeorge.quoteme.R;

/**
 * Created by George Benjamin on 7/24/2018.
 */

public class BackgroundImageAdapter extends RecyclerView.Adapter<BackgroundImageAdapter.ViewHolder> {

    private Context mContext;
    private int[] mImages;
    private OnBackgroundImageItemClicked mListener;

    public interface OnBackgroundImageItemClicked{
        void onImageItemClicked(int imagePosition);
    }

    public BackgroundImageAdapter(Context context, int[] images, OnBackgroundImageItemClicked listener){
        mContext = context;
        mImages = images;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.background_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.backgroundImage.setImageResource(mImages[position]);
    }

    @Override
    public int getItemCount() {
        return mImages.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView backgroundImage;

        public ViewHolder(View itemView) {
            super(itemView);
            backgroundImage = (ImageView) itemView.findViewById(R.id.backgroundImage);
            backgroundImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onImageItemClicked(getAdapterPosition());
        }
    }
}
