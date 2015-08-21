package com.drivingevaluate.model;

public class Order {
    private String orderNo;
    private int goodsId;
    private int sid;
    private int totalFee;
    private String goodsTitle;
    private String photoPath;
    private String sname;

    private long createdTime;
    private int prePay;//预付价
    private int status;//订单状态 1表示成功 0表示失败
    private int judgeStatus;

    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public int getPrePay() {
        return prePay;
    }

    public void setPrePay(int prePay) {
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
}
