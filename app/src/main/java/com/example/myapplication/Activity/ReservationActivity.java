package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.myapplication.Common.Common;
import com.example.myapplication.LinearLayoutManagerWrapper;
import com.example.myapplication.Model.SeatModel;
import com.example.myapplication.R;
import com.example.myapplication.ViewHolder.SeatViewHolder;
import com.example.myapplication.databinding.ActivityReservationBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ReservationActivity extends AppCompatActivity {

    ActivityReservationBinding binding;

    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseRecyclerAdapter<SeatModel, SeatViewHolder> adapter;
    FirebaseRecyclerOptions<SeatModel> options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReservationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        innit();
    }

    private void innit() {
        binding.returnReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        Query query = reference.child(Common.RESERVATION_REFERENCE).child("1").orderByChild("enable");
        options = new FirebaseRecyclerOptions.Builder<SeatModel>()
                .setQuery(query, SeatModel.class)
                .setLifecycleOwner(this)
                .build();
        adapter = new FirebaseRecyclerAdapter<SeatModel, SeatViewHolder>(options) {


            @NonNull
            @Override
            public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
                return new SeatViewHolder(view);
            }



            @Override
            protected void onBindViewHolder(@NonNull SeatViewHolder holder, int position, @NonNull SeatModel model) {
                holder.seat.setText(model.getSeat());
                if(!model.isEnable()) {
                    holder.status.setText("Còn trống");
                    holder.status.setTextColor(getColor(R.color.lime));
                    holder.reservationButton.setEnabled(true);
                    holder.reservationButton.setBackground(getDrawable(R.drawable.enable_bt));
                    holder.noteView.getBackground().setColorFilter(getColor(R.color.lime), PorterDuff.Mode.SRC_IN);
                }
                else {
                    holder.status.setText("Đang sử dụng");
                    holder.status.setTextColor(getColor(R.color.danger_color));
                    holder.reservationButton.setEnabled(false);
                    holder.reservationButton.setBackground(getDrawable(R.drawable.disable_bt));
                    holder.noteView.getBackground().setColorFilter(getColor(R.color.danger_color), PorterDuff.Mode.SRC_IN);
                }
                holder.seatInfor.setText("Loại máy: " + model.getTypeCom()
                        + "\nLoại phòng: "
                        + model.getTypeRoom() + "\nTrạng thái: " + model.getStatus());

                holder.reservationButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ReservationActivity.this, ConfirmReservationActivity.class);
                        intent.putExtra("seatInfo", model);
                        startActivity(intent);
                    }
                });
            }
        };

        binding.reservationRecyclerview.setLayoutManager(new LinearLayoutManagerWrapper(this, LinearLayoutManager.VERTICAL,false));
        binding.reservationRecyclerview.setAdapter(adapter);

        binding.spinnerGrade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String grade = "1";
                if(i == 1) {
                    grade = "2";
                }
                else if(i==2) {
                    grade = "3";
                }
                FirebaseRecyclerOptions newOption = getNewOption(grade);
                adapter.updateOptions(newOption);
                binding.reservationRecyclerview.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private FirebaseRecyclerOptions getNewOption(String grade) {
        return new FirebaseRecyclerOptions.Builder<SeatModel>()
                .setQuery(reference.child(Common.RESERVATION_REFERENCE).child(grade).orderByChild("enable")
                        ,SeatModel.class)
                .setLifecycleOwner(this)
                .build();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(adapter!=null) adapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        if(adapter!=null) adapter.stopListening();
//        super.onStop();
//    }
}