package com.example.teamtest_ver10.util;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String getNowDateTime(String formatStr){
        String format=formatStr;
        if(format==null||formatStr.length()<=0){
            format="yyyyMMddHHmmss";
        }
        SimpleDateFormat s_format=new SimpleDateFormat(format);
        return s_format.format(new Date());
    }

    public static String getNowTime(){
        SimpleDateFormat s_format=new SimpleDateFormat("HH:mm:ss");
        return s_format.format(new Date());
    }

    public static String getNowTimeDetail(){
        SimpleDateFormat s_format=new SimpleDateFormat("HH:mm:ss.sss");//这个地方对日期格式要求是怎样的不知道
        return s_format.format(new Date());
    }
}
