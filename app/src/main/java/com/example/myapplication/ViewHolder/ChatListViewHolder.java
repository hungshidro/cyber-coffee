package com.example.myapplication.ViewHolder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class ChatListViewHolder extends RecyclerView.ViewHolder {

    public TextView username,lastMess,lastDate;
    public LinearLayout layout;

    public ChatListViewHolder(@NonNull View itemView) {
        super(itemView);
        username = itemView.findViewById(R.id.user_name_tv);
        lastMess = itemView.findViewById(R.id.last_message_tv);
        lastDate = itemView.findViewById(R.id.last_date_tv);
        layout = itemView.findViewById(R.id.main_layout);
    }
}
