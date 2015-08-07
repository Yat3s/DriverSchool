package com.drivingevaluate.model;

import java.io.Serializable;

public class DSchool implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String sid;
    private String sname;
    private float score;
    private String address;
    private String district;
    private int studyingAmount;//正在学习的学生数量
    private int studentAmount;//总共学习过的学生数量
    private int coachAmount;//教练数量
    private String tel;//电话
    private String createdTime;
    private String imgPaths;//全部驾校图片
    private String longitude;
    private String latitude;
    private String introduction;//驾校简介
    private String photoPath;//驾校预览图

    private float marketPrice;//市场价格
    private float ourPrice;//我们的价格
    private float prePay;//预付款

    private Float distance;
    private int costTime;

    public int getCostTime() {
        return costTime;
    }

    public void setCostTime(int costTime) {
        this.costTime = costTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getStudyingAmount() {
        return studyingAmount;
    }

    public void setStudyingAmount(int studyingAmount) {
        this.studyingAmount = studyingAmount;
    }

    public int getStudentAmount() {
        return studentAmount;
    }

    public void setStudentAmount(int studentAmount) {
        this.studentAmount = studentAmount;
    }

    public int getCoachAmount() {
        return coachAmount;
    }

    public void setCoachAmount(int coachAmount) {
        this.coachAmount = coachAmount;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getImgPaths() {
        return imgPaths;
    }

    public void setImgPaths(String imgPaths) {
        this.imgPaths = imgPaths;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public float getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(float marketPrice) {
        this.marketPrice = marketPrice;
    }

    public float getOurPrice() {
        return ourPrice;
    }

    public void setOurPrice(float ourPrice) {
        this.ourPrice = ourPrice;
    }

    public float getPrePay() {
        return prePay;
    }

    public void setPrePay(float prePay) {
        this.prePay = prePay;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

}
