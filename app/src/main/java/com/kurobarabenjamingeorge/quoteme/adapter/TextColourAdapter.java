package com.kurobarabenjamingeorge.quoteme.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kurobarabenjamingeorge.quoteme.R;

/**
 * Created by George Benjamin on 7/31/2018.
 */

public class TextColourAdapter extends RecyclerView.Adapter<TextColourAdapter.ViewHolder> {

    private Context ctx;
    private int[] colours;
    private OnColourListPicked mInterface;

    public interface OnColourListPicked{
        void onColourPicked(int position);
    }

    public TextColourAdapter(Context context, int[] data, OnColourListPicked mInterface){
        this.ctx = context;
        this.colours = data;
        this.mInterface = mInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.colour_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.colourImageView.setBackgroundResource(colours[position]);

    }

    @Override
    public int getItemCount() {
        return colours.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView colourImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            colourImageView = (ImageView) itemView.findViewById(R.id.colourIcon);

            colourImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mInterface.onColourPicked(getAdapterPosition());
                }
            });
        }
    }
}
