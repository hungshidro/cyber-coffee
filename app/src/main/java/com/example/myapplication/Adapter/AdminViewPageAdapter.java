package com.example.myapplication.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapplication.Fragment.ChatAdminFragment;
import com.example.myapplication.Fragment.ChatFragment;
import com.example.myapplication.Fragment.HomeAdminFragment;
import com.example.myapplication.Fragment.HomeFragment;
import com.example.myapplication.Fragment.PersonalAdminFragment;
import com.example.myapplication.Fragment.PersonalFragment;
import com.example.myapplication.Fragment.RankFragment;

public class AdminViewPageAdapter extends FragmentStatePagerAdapter {
    public AdminViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeAdminFragment();
            case 1:
                return new ChatAdminFragment();
            case 2:
                return new PersonalAdminFragment();
        }
        return new HomeAdminFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
