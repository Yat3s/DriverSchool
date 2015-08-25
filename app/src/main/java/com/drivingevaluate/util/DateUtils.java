package com.drivingevaluate.util;

import android.content.Context;
import android.util.Log;

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
        Date date = new Date(timestamp);
        String dateStr = sdf.format(new Date(timestamp));
        if (date.getDay() == new Date().getDay()) {
            Log.e("Yat3s","日期："+date.getDay()+":"+new Date().getDay());
            return "今天 " + dateStr;
        }
        else {
            return "昨天 " + dateStr;
        }
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

    public static String getInterval(long timestamp) {
        Date createAt = new Date(timestamp);
        //定义最终返回的结果字符串。
        String interval = null;

        long millisecond = new Date().getTime() - timestamp;

        long second = millisecond / 1000;

        if (second <= 0) {
            second = 0;
        }

        if (second == 0) {
            interval = "刚刚";
        } else if (second < 30) {
            interval = second + "秒以前";
        } else if (second >= 30 && second < 60) {
            interval = "半分钟前";
        } else if (second >= 60 && second < 60 * 60) {
            long minute = second / 60;
            interval = minute + "分钟前";
        } else if (second >= 60 * 60 && second < 60 * 60 * 24) {
            long hour = (second / 60) / 60;
            if (hour <= 3) {
                interval = hour + "小时前";
            } else {
                interval = "今天" + getFormatTime(createAt, "hh:mm");
            }
        } else if (second >= 60 * 60 * 24 && second <= 60 * 60 * 24 * 2) {
            interval = "昨天" + getFormatTime(createAt, "hh:mm");
        } else if (second >= 60 * 60 * 24 * 2 && second <= 60 * 60 * 24 * 7) {
            long day = ((second / 60) / 60) / 24;
            interval = day + "天前";
        } else if (second >= 60 * 60 * 24 * 7) {
            interval = getFormatTime(createAt, "MM-dd hh:mm");
        } else if (second >= 60 * 60 * 24 * 365) {
            interval = getFormatTime(createAt, "YYYY-MM-dd hh:mm");
        } else {
            interval = "0";
        }
        // 最后返回处理后的结果。
        return interval;
    }

    public static String getFormatTime(Date date, String Sdf) {
        return (new SimpleDateFormat(Sdf)).format(date);
    }
}