package com.drivingevaluate.util;

/**
 * Created by Yat3s on 8/22/15.
 * Email:hawkoyates@gmail.com
 */
public class Infliter {
    public static String statusInfliter(int status){
        if (status == 1)
            return "未学车";
        if (status == 2)
            return "学车中";
        if (status == 3)
            return "已拿驾照";
        if (status == 4)
            return "开车开的起飞";
        return "外星人";
    }
}
