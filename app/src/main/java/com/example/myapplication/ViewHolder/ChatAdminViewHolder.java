package com.example.myapplication.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class ChatAdminViewHolder extends RecyclerView.ViewHolder {

    public TextView date_admin_tv, content_admin_tv;
    public ChatAdminViewHolder(@NonNull View itemView) {
        super(itemView);
        date_admin_tv = itemView.findViewById(R.id.date_sender_chat_tv);
        content_admin_tv = itemView.findViewById(R.id.chat_admin_tv);
    }
}
