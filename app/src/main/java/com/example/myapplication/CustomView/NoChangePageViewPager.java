package com.example.myapplication.CustomView;

import android.content.Context;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class NoChangePageViewPager extends ViewPager {

    private boolean isChangePaging;

    public NoChangePageViewPager(@NonNull Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return this.isChangePaging && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.isChangePaging && super.onInterceptTouchEvent(ev);
    }

    public void setChangePaging(boolean changePaging) {
        isChangePaging = changePaging;
    }
}
