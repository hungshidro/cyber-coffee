package com.example.myapplication.Model;

import android.graphics.drawable.Drawable;

public class Action {
    private Drawable drawable;
    private String tittle;

    public Action(Drawable drawable, String tittle) {
        this.drawable = drawable;
        this.tittle = tittle;
    }

    public Action() {
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }
}
