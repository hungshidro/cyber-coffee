package com.example.myapplication.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class ChatUserViewHolder extends RecyclerView.ViewHolder {

    public TextView date_user_tv,content_user_tv;
    public ChatUserViewHolder(@NonNull View itemView) {
        super(itemView);
        date_user_tv = itemView.findViewById(R.id.date_reciever_chat_tv);
        content_user_tv = itemView.findViewById(R.id.chat_user_tv);
    }
}
