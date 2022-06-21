package com.example.myapplication.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AppListener.ItemClickListener;
import com.example.myapplication.R;

public class ActionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView img;
    public TextView tv;
    ItemClickListener itemClickListener;

    public ActionViewHolder(@NonNull View itemView) {
        super(itemView);
        img = itemView.findViewById(R.id.img_action);
        tv = itemView.findViewById(R.id.tv_action);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onItemClick(view, getAdapterPosition());
    }
}
