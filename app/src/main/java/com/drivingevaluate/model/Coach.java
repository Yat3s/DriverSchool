package com.drivingevaluate.model;


import com.google.gson.annotations.SerializedName;

public class Coach {
    private String goodsTitle;
    private String photoPath;
    private String sellerName;

    @SerializedName("discountPirce")
    private int discountPrice;
    private int goodsId;
    private double item1;
    private double item2;
    private double item3;
    private double item4;
    private int judgeCount;
    private int marketPrice;
    private double prepayPrice;
    private int sellCount;

    private int merchantId;
    private String merchantName;

    private long createdTime;

    public double getAvgGrade() {
        return (item1 + item2 + item3) / 3.0;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
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

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public double getItem1() {
        return item1;
    }

    public void setItem1(double item1) {
        this.item1 = item1;
    }

    public double getItem2() {
        return item2;
    }

    public void setItem2(double item2) {
        this.item2 = item2;
    }

    public double getItem3() {
        return item3;
    }

    public void setItem3(double item3) {
        this.item3 = item3;
    }

    public double getItem4() {
        return item4;
    }

    public void setItem4(double item4) {
        this.item4 = item4;
    }

    public int getJudgeCount() {
        return judgeCount;
    }

    public void setJudgeCount(int judgeCount) {
        this.judgeCount = judgeCount;
    }

    public int getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(int marketPrice) {
        this.marketPrice = marketPrice;
    }

    public double getPrepayPrice() {
        return prepayPrice;
    }

    public void setPrepayPrice(double prepayPrice) {
        this.prepayPrice = prepayPrice;
    }

    public int getSellCount() {
        return sellCount;
    }

    public void setSellCount(int sellCount) {
        this.sellCount = sellCount;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }
}
