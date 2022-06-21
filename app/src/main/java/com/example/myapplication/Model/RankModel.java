package com.example.myapplication.Model;

public class RankModel {
    private int rankpoint;
    private String name;

    public RankModel() {
        name = "Topaz";
    }

    public int getRankpoint() {
        return rankpoint;
    }

    public void setRankpoint(int rankpoint) {
        this.rankpoint = rankpoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
