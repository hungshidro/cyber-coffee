package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.os.Bundle;

import com.example.myapplication.Adapter.AdminViewPageAdapter;
import com.example.myapplication.Adapter.PageAdapter;
import com.example.myapplication.PageTranform.ZoomOutPageTranform;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityAdminBinding;

public class AdminActivity extends AppCompatActivity {

    ActivityAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.viewPagerAdmin.setAdapter(new AdminViewPageAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        binding.viewPagerAdmin.setPageTransformer(true, new ZoomOutPageTranform());
        binding.bottomBarAdmin.setupWithViewPager(binding.viewPagerAdmin);
    }
}