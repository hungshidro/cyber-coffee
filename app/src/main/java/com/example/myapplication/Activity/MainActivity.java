package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.Adapter.ActionAdapter;
import com.example.myapplication.Adapter.PageAdapter;
import com.example.myapplication.PageTranform.ZoomOutPageTranform;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.databinding.HomeFragmentBinding;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        binding.viewPager.setPageTransformer(true, new ZoomOutPageTranform());
        binding.bottomBar.setupWithViewPager(binding.viewPager);
        
    }


}