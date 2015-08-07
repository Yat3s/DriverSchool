package com.drivingevaluate.model;

import java.util.Date;

public class Order {
    private int orderId;
    private int userId;
    private int dSchoolId;
    private String dSchoolName;

    private int coachId;
    private String coachName;

    private String createTime;
    private float prePay;//预付价
    private int status;//订单状态 1表示成功 0表示失败
    private int judgeStatus;
}
