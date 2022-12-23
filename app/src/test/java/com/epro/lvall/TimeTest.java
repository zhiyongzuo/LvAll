package com.epro.lvall;

import com.blankj.utilcode.util.TimeUtils;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeTest {

    @Test
    public void test() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
//
//        // 获取当前日期
//        System.out.println(sdf.format(calendar.getTime()));
//
//        // 获取一个月前的日期
//        calendar.add(Calendar.MONTH, -34);
//        String endDate = sdf.format(calendar.getTime());
//        System.out.println(endDate);
//
//        calendar.add(Calendar.DAY_OF_MONTH, 16);
//        String endDate2 = sdf.format(calendar.getTime());
//        System.out.println(endDate2);
//
//        calendar.add(Calendar.MONTH, -12);
//        String endDate3 = sdf.format(calendar.getTime());
//        System.out.println(endDate3);

        calendar.setTimeInMillis(TimeUtils.date2Millis(TimeUtils.string2Date("2006-06-06 06:06:06")));
        System.out.println(sdf.format(calendar.getTime()));//2006-06-06

        calendar.add(Calendar.MONTH, -10);
        String endDate3 = sdf.format(calendar.getTime());
        System.out.println(endDate3);//2005-08-06
    }
}
