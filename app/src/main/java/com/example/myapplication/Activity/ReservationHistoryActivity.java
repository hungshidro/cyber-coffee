package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Common.Common;
import com.example.myapplication.LinearLayoutManagerWrapper;
import com.example.myapplication.Model.ReservationModel;
import com.example.myapplication.Model.UserModel;
import com.example.myapplication.R;
import com.example.myapplication.Common.Utils;
import com.example.myapplication.ViewHolder.ReservationViewHolder;
import com.example.myapplication.databinding.ActivityReservationHistoryBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ReservationHistoryActivity extends AppCompatActivity {

    ActivityReservationHistoryBinding binding;

    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseRecyclerAdapter<ReservationModel, ReservationViewHolder> adapter;
    FirebaseRecyclerOptions<ReservationModel> options;

    UserModel user = Common.currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReservationHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        innit();
    }

    private void innit() {

        Log.e("rankpoint", Common.rankUser.getRankpoint()+" history");
        binding.returnReservationHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        Query query = reference.child(Common.RESERVATION_HISTORY_REFERENCE)
                .child(user.getUid());

        options = new FirebaseRecyclerOptions.Builder<ReservationModel>()
                .setQuery(query, ReservationModel.class)
                .setLifecycleOwner(this)
                .build();

        adapter = new FirebaseRecyclerAdapter<ReservationModel, ReservationViewHolder>(options) {
            @NonNull
            @Override
            public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_reservation_history, parent, false);
                return new ReservationViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ReservationViewHolder holder, int position, @NonNull ReservationModel model) {
                if(holder != null && model != null){
                    holder.tv_seat.setText(model.getSeat());
                    boolean isEnable = (System.currentTimeMillis() > model.getTimeMillis())?false:true;
                    holder.tv_detail.setText(
                            model.getTypeCom() +"\n"+
                            model.getTypeRoom() + "\n" +
                            ((isEnable)?"Đang đặt":"Hết hạn")+"\n" +
                            Utils.timeMillisToDate(model.getTimeMillis())+"\n" +
                            model.getFee()
                    );
                    holder.bt_detail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ReservationHistoryActivity.this, DetailReservationHistoryActivity.class);
                            intent.putExtra("detail", model);
                            startActivity(intent);
                        }
                    });
                }
            }
        };

        binding.reservationHistoryRecyclerview.setLayoutManager(new LinearLayoutManagerWrapper(this, LinearLayoutManager.VERTICAL,false));
        binding.reservationHistoryRecyclerview.setAdapter(adapter);
    }
}