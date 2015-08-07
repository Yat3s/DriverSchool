package com.drivingevaluate.model;

import java.util.Date;

public class UserBuyOrder {

    private String orderNo;
    private int sid;
    private String sname;
    private int goodsId;
    private String goodsTitle;
    private String photoPath;
    private Date createTime;
    private float totalFee;
    private int status;
    private int judgeStatus;

    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public int getSid() {
        return sid;
    }
    public void setSid(int sid) {
        this.sid = sid;
    }
    public String getSname() {
        return sname;
    }
    public void setSname(String sname) {
        this.sname = sname;
    }
    public int getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
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
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public float getTotalFee() {
        return totalFee;
    }
    public void setTotalFee(float totalFee) {
        this.totalFee = totalFee;
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
