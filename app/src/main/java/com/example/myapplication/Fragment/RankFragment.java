package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Common.Common;
import com.example.myapplication.Model.RankModel;
import com.example.myapplication.Model.UserModel;
import com.example.myapplication.R;
import com.example.myapplication.Common.RankUtil;
import com.example.myapplication.databinding.RankFragmentBinding;

public class RankFragment extends Fragment {

    RankFragmentBinding binding;

    UserModel user = Common.currentUser;
    RankModel rank = Common.rankUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = RankFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        binding.rankProgessRank.setProgress(RankUtil.getProgress(rank.getRankpoint()));
        binding.tvRankRank.setText("Hạng thành viên: " + rank.getName());
        binding.tvRankPointRank.setText(rank.getRankpoint()+"");
        switch (rank.getName()){
            case "Topaz": binding.icRankRank.setImageDrawable(getResources().getDrawable(R.drawable.icons8_topaz_64)); break;
            case "Ruby": binding.icRankRank.setImageDrawable(getResources().getDrawable(R.drawable.icons8_ruby_64)); break;
            case "Diamond": binding.icRankRank.setImageDrawable(getResources().getDrawable(R.drawable.icons8_diamond_64)); break;
        }
    }
}
