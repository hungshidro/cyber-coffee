package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.myapplication.Common.Common;
import com.example.myapplication.Model.RankModel;
import com.example.myapplication.Model.ReservationModel;
import com.example.myapplication.Model.SeatModel;
import com.example.myapplication.Model.UserModel;
import com.example.myapplication.Common.RankUtil;
import com.example.myapplication.Common.Utils;
import com.example.myapplication.databinding.ActivityConfirmReservationBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfirmReservationActivity extends AppCompatActivity {

    ActivityConfirmReservationBinding binding;

    FirebaseDatabase database;
    DatabaseReference reference;

    UserModel user = Common.currentUser;

    SeatModel seat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmReservationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        innit();
        setView();
    }

    private void setView() {
        Intent intent = getIntent();
        seat = (SeatModel) intent.getSerializableExtra("seatInfo");
        binding.reservationDetail.setText(
                seat.getSeat() +"\n" +
                seat.getTypeCom() + "\n"+
                seat.getTypeRoom()+ "\n"+
                seat.getStatus());
        binding.feeReservation.setText("0");
        binding.noteTime.setText("");
        binding.edtTimeReservation.setFocusable(true);
        binding.confirmButtonReservation.setEnabled(false);
        binding.timeTv.setText("");
    }

    private void innit() {

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child(Common.RESERVATION_HISTORY_REFERENCE).child(user.getUid());

        binding.edtTimeReservation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()!= 0){
                    int time = Integer.parseInt(binding.edtTimeReservation.getText().toString());
                    if(time < 60) {
                        binding.noteTime.setText("Tối thiểu 60");
                        binding.timeTv.setText("");
                    }
                    else
                    {
                        if(time%30 !=0)
                        {
                            binding.confirmButtonReservation.setEnabled(false);
                            binding.noteTime.setText("Chia hết cho 30");
                            binding.timeTv.setText("");
                        }
                        else {
                            binding.confirmButtonReservation.setEnabled(true);
                            binding.noteTime.setText("");
                            int fee = time/30*5000;
                            String rank = Common.rankUser.getName();
                            if(rank == "Ruby") fee = fee*90/100;
                            else if(rank == "Diamond") fee = fee*75/100;
                            binding.feeReservation.setText(fee+"");
                            binding.timeTv.setText(Utils.getTimeReservation(time));
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.returnReservationConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.cancelButtonReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.confirmButtonReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int time = Integer.parseInt(binding.edtTimeReservation.getText().toString());
                int fee = Integer.parseInt(binding.feeReservation.getText().toString());
                String key = reference.push().getKey();
                ReservationModel reservationModel = new ReservationModel(seat, Utils.toMillis(time),fee);
                reservationModel.setId(key);
                reference.child(key).setValue(reservationModel);
                FirebaseDatabase.getInstance().getReference()
                        .child(Common.RESERVATION_REFERENCE)
                        .child(seat.getSeat().substring(0,1))
                        .child(seat.getId())
                        .child("enable").setValue(true);
                int point = RankUtil.getPoint(time);
                RankModel rankModel = Common.rankUser;
                int rankPoint = rankModel.getRankpoint()+point;
                rankModel.setRankpoint(rankPoint);
                String rankName = RankUtil.getrRank(rankPoint);
                rankModel.setName(rankName);
                Common.rankUser = rankModel;
                database.getReference().child(Common.RANK_REFERENCE)
                        .child(user.getUid())
                        .setValue(rankModel);
                finish();
            }
        });
    }
}