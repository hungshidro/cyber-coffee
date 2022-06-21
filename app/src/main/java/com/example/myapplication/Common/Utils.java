package com.example.myapplication.Common;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {

    public static String timeMillisToDate(Long timeMillis){
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        String time = formatter.format(calendar.getTime());
        return time;
    }

    public static String getTimeReservation(int minute){
        Long timemillis = System.currentTimeMillis();
        int addmillis = minute*60000;
        Long finalTime = timemillis + addmillis;
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(finalTime);
        String reservationTime = formatter.format(calendar.getTime());
        return reservationTime;
    }

    public static Long toMillis(int minute){
        return System.currentTimeMillis() + minute*60000;
    }
}
