package com.example.myapplication.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class SeatViewHolder extends RecyclerView.ViewHolder {

    public TextView seat, status, seatInfor;
    public Button reservationButton;
    public View noteView;
    public SeatViewHolder(@NonNull View itemView) {
        super(itemView);
        seat = itemView.findViewById(R.id.tv_seat);
        status = itemView.findViewById(R.id.tv_status);
        seatInfor = itemView.findViewById(R.id.tv_info_seat);
        reservationButton = itemView.findViewById(R.id.reservation_button);
        noteView = itemView.findViewById(R.id.note_view);
    }
}
