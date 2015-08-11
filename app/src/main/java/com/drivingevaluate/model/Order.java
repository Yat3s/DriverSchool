package com.drivingevaluate.model;

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

    private String result;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getdSchoolId() {
        return dSchoolId;
    }

    public void setdSchoolId(int dSchoolId) {
        this.dSchoolId = dSchoolId;
    }

    public String getdSchoolName() {
        return dSchoolName;
    }

    public void setdSchoolName(String dSchoolName) {
        this.dSchoolName = dSchoolName;
    }

    public int getCoachId() {
        return coachId;
    }

    public void setCoachId(int coachId) {
        this.coachId = coachId;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public float getPrePay() {
        return prePay;
    }

    public void setPrePay(float prePay) {
        this.prePay = prePay;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getJudgeStatus() {
        return judgeStatus;
    }

    public void setJudgeStatus(int judgeStatus) {
        this.judgeStatus = judgeStatus;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
