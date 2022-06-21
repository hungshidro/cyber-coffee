package com.example.myapplication.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class ReservationViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_seat,tv_detail;
    public ImageView bt_detail;

    public ReservationViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_seat = itemView.findViewById(R.id.tv_seat_history);
        tv_detail = itemView.findViewById(R.id.tv_detail_reservation);
        bt_detail = itemView.findViewById(R.id.detail_reservation_img);
    }
}
