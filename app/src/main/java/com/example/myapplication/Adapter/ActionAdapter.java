package com.example.myapplication.Adapter;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.example.myapplication.AppListener.ItemClickListener;
import com.example.myapplication.Model.Action;
import com.example.myapplication.R;
import com.example.myapplication.ViewHolder.ActionViewHolder;

public class ActionAdapter extends RecyclerView.Adapter<ActionViewHolder> {

    ArrayList<Action> list;
    ItemClickListener itemClickListener;

    public ActionAdapter( ArrayList<Action> list, ItemClickListener itemClickListener) {
        this.list = list;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ActionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_action,parent,false);
        return new ActionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActionViewHolder holder, int position) {

        Action action = list.get(position);
        if(action == null) return;
        holder.img.setImageDrawable(action.getDrawable());
        holder.tv.setText(action.getTittle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        itemClickListener = itemClickListener;
    }
}
