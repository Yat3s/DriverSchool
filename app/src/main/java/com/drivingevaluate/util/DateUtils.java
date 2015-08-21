package com.drivingevaluate.util;

import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static DateFormat format = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss");
    public static String yyyymmddhhmmss = "yyyy-MM-dd HH:mm:ss";

    public static String formatDate(Context paramContext, long paramLong) {
        return android.text.format.DateUtils.formatDateTime(paramContext, paramLong, 527121);
    }

    public static Date getDate(String paramString) {
        try {
            Date localDate = format.parse(paramString);
            return localDate;
        } catch (ParseException localParseException) {
            localParseException.printStackTrace();
        }
        return null;
    }

    public static String getStrDate(Date paramDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(paramDate);
    }

    public static String getDateStr(Date paramDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(paramDate);
    }

    public static String getDayDateStr(Long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        String date = sdf.format(new Date(timestamp));
        return date;
    }

    public static String getHourDateStr(Long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String date = sdf.format(new Date(timestamp));
        return "昨天 "+date;
    }

    public static String getStandardDate(long timestamp) {
        StringBuffer sb = new StringBuffer();
        long t = timestamp / 1000;
        long time = System.currentTimeMillis() - (t * 1000);
        long mill = (long) Math.ceil(time / 1000);//秒前

        long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前

        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时

        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前
        if (day - 1 > 3) {
            return getDayDateStr(timestamp);
        }
        if (day - 1 > 0) {
            sb.append(day + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                return getHourDateStr(timestamp);
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }

    public static String getStandardDate(Date date) {

        StringBuffer sb = new StringBuffer();

        long t = date.getTime() / 1000;
        long time = System.currentTimeMillis() - (t * 1000);
        long mill = (long) Math.ceil(time / 1000);//秒前

        long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前

        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时

        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

        if (day - 1 > 0) {
            sb.append(day + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }
}