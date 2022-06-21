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

import com.example.myapplication.Activity.ReservationActivity;
import com.example.myapplication.Adapter.ActionAdapter;
import com.example.myapplication.Common.Common;
import com.example.myapplication.Model.RankModel;
import com.example.myapplication.Model.UserModel;
import com.example.myapplication.Common.RankUtil;
import com.example.myapplication.databinding.HomeFragmentBinding;
import com.google.firebase.database.FirebaseDatabase;

import ru.nikartm.support.BadgePosition;

public class HomeFragment extends Fragment {

    HomeFragmentBinding binding;
    ActionAdapter actionAdapter1;
    ActionAdapter actionAdapter2;

    UserModel user = Common.currentUser;
    RankModel rank = Common.rankUser;
    int unReadNotify = 0;

    FirebaseDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        innit();
        setView();
    }

    private void setView() {
        Log.e("rankpoint", Common.rankUser.getRankpoint()+" home");
        binding.tvNameHome.setText(user.getLastName() + " " + user.getFirtName());
        binding.tvRankHome.setText("Hạng thành viên: " + rank.getName());
        binding.tvRankPointHome.setText(rank.getRankpoint() + "");
        if(unReadNotify > 0) binding.notifycation.setBadgeValue(unReadNotify);
        binding.rankProgessHome.setProgress(RankUtil.getProgress(rank.getRankpoint()));

    }

    private int getnotify() {
        int notify = 0;
        return notify;
    }

    private void innit() {



        database = FirebaseDatabase.getInstance();

        //notification
        binding.notifycation.setBadgeTextSize(12)
                    .setBadgePosition(BadgePosition.TOP_RIGHT)
                    .setShowCounter(true)
                    .setBadgePadding(4);

        binding.actionReservationHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ReservationActivity.class));
            }
        });
    }
}
