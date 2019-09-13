package com.it529.teamgy.pharmacyapp.util;

import org.apache.http.impl.cookie.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Util {

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public static String getTodayDateString() {
        Date today = new Date();
        System.out.println(sdf.format(today));

        return sdf.format(today);
    }

    public static Date getTodayDate() {
        Calendar today = Calendar.getInstance();
        Date todayDate = today.getTime();

        return todayDate;
    }
}
