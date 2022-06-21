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
import com.example.myapplication.Model.UserModel;
import com.example.myapplication.R;
import com.example.myapplication.Common.RankUtil;
import com.example.myapplication.Common.Utils;
import com.example.myapplication.databinding.ActivityDetailReservationHistoryBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailReservationHistoryActivity extends AppCompatActivity {

    ActivityDetailReservationHistoryBinding binding;

    FirebaseDatabase database;
    DatabaseReference reference;

    UserModel user = Common.currentUser;

    ReservationModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailReservationHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        innit();
        setView();
    }

    private void setView() {
        Intent intent = getIntent();
        model = (ReservationModel) intent.getSerializableExtra("detail");
        boolean isEnable = (System.currentTimeMillis() > model.getTimeMillis())?false:true;
        binding.reservationDetail.setText(
                model.getSeat() +"\n" +
                        model.getTypeCom() + "\n"+
                        model.getTypeRoom()+ "\n"+
                        ((isEnable)?"Đang đặt":"Hết hạn"));
        binding.feeReservation.setText("0");
        binding.noteTime.setText("");
        binding.feeReservation.setText("");
        if (isEnable) binding.edtTimeReservation.setFocusable(true);
        else binding.edtTimeReservation.setFocusable(false);
        binding.confirmButtonReservation.setEnabled(false);
        binding.confirmButtonReservation.setBackground(getDrawable(R.drawable.disable_bt));
        binding.timeTv.setText(Utils.timeMillisToDate(model.getTimeMillis())+"\n" +model.getFee());
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
                        binding.feeReservation.setText("");
                        binding.noteTime.setText("Tối thiểu 60");
                        binding.confirmButtonReservation.setEnabled(false);
                        binding.confirmButtonReservation.setBackground(getDrawable(R.drawable.disable_bt));
                        binding.timeTv.setText(Utils.timeMillisToDate(model.getTimeMillis())+"\n" +model.getFee());
                    }
                    else
                    {
                        if(time%30 !=0)
                        {
                            binding.feeReservation.setText("");
                            binding.confirmButtonReservation.setEnabled(false);
                            binding.confirmButtonReservation.setBackground(getDrawable(R.drawable.disable_bt));
                            binding.noteTime.setText("Chia hết cho 30");
                            binding.timeTv.setText(Utils.timeMillisToDate(model.getTimeMillis())+"\n" +model.getFee());
                        }
                        else {
                            binding.confirmButtonReservation.setEnabled(true);
                            binding.confirmButtonReservation.setBackground(getDrawable(R.drawable.enable_bt));
                            binding.noteTime.setText("");
                            int fee = time/30*5000;
                            String rank = Common.rankUser.getName();
                            if(rank == "Ruby") fee = fee*90/100;
                            else if(rank == "Diamond") fee = fee*75/100;
                            binding.feeReservation.setText(fee+"");
                            binding.timeTv.setText(Utils.timeMillisToDate(time*60000+model.getTimeMillis())
                            +"\n" + (model.getFee() + Integer.parseInt(binding.feeReservation.getText().toString())));
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
                model.setFee(fee + model.getFee());
                model.setTimeMillis(time*60000 + model.getTimeMillis());
                reference.child(model.getId()).setValue(model);
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