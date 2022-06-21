package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Activity.ReservationHistoryActivity;
import com.example.myapplication.Activity.StartActivity;
import com.example.myapplication.Common.Common;
import com.example.myapplication.Model.RankModel;
import com.example.myapplication.Model.SeatModel;
import com.example.myapplication.Model.UserModel;
import com.example.myapplication.databinding.PersonalFragmentBinding;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PersonalFragment extends Fragment {

    PersonalFragmentBinding binding;

    UserModel user = Common.currentUser;
    RankModel rank = Common.rankUser;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PersonalFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        innit();
        setView();
    }

    private void setView() {
        binding.tvNamePersonal.setText(user.getLastName() + " " + user.getFirtName());
        binding.tvRankPeronal.setText("Thành viên " + rank.getName());
    }

    private void innit() {

        binding.btSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), StartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        


        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child(Common.RESERVATION_REFERENCE);

        binding.reservationHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ReservationHistoryActivity.class));
            }
        });
    }
}
