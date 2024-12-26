package com.liu.club_ms.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CompareTime {
    public static boolean time1after2(String time1, String time2){
        // 定义时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 将字符串解析为 LocalDateTime
        LocalDateTime dateTime1 = LocalDateTime.parse(time1, formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(time2, formatter);

        // 比较两个时间
        return dateTime1.isAfter(dateTime2);
    }
}
