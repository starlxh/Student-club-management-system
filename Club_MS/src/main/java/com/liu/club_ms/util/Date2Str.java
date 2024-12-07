package com.liu.club_ms.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Date2Str {
    public static String getNowDate(Date nowDate){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(nowDate);
    }
}
