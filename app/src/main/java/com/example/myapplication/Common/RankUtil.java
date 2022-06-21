package com.example.myapplication.Common;

public class RankUtil {

    private static final int RUBY = 1000;
    private static final int DIAMOND = 5000;
    public static String getrRank(int point){
        if(point < RUBY) return "Topaz";
        else if(point < DIAMOND) return "Ruby";
        else return "Diamond";
    }

    public static int getPoint(int time){
        return time*10/30;
    }

    public static int getProgress(int rankPoint){
        if(rankPoint < 1000) return rankPoint*100/1000;
        else if(rankPoint >= 1000) {
            int progress = (rankPoint - 1000)*100/4000;
            if (progress < 0) progress++;
            return progress;
        }
        return 100;
    }
}
