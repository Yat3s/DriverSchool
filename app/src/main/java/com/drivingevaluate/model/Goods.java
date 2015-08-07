package com.drivingevaluate.model;

import java.util.Date;

public class Goods {

    private Long goodsId;
    private Integer merchantId;
    private String goodsTitle;
    private Float discountPirce;//打折价
    private Float marketPrice;
    private Float prepayPrice;//预付
    private String desc;
    private Date deadLine;
    private int latestBuyerCount;//最近购买人数
    private String sellerName;
    private Integer carType;
    private Integer city;
    private Integer area;
    private Integer recommendation;//推荐指数
    private Integer fidesGrade;//信用等级
    private String introduction;//商品介绍
    private String refundIntro;//退费说明
    private String photoPath;//商品照片

    public Long getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
    public String getGoodsTitle() {
        return goodsTitle;
    }
    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }
    public Float getDiscountPirce() {
        return discountPirce;
    }
    public void setDiscountPirce(Float discountPirce) {
        this.discountPirce = discountPirce;
    }
    public Float getMarketPrice() {
        return marketPrice;
    }
    public void setMarketPrice(Float marketPrice) {
        this.marketPrice = marketPrice;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public Date getDeadLine() {
        return deadLine;
    }
    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }
    public String getSellerName() {
        return sellerName;
    }
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }
    public Integer getCarType() {
        return carType;
    }
    public void setCarType(Integer carType) {
        this.carType = carType;
    }
    public Integer getCity() {
        return city;
    }
    public void setCity(Integer city) {
        this.city = city;
    }
    public Integer getArea() {
        return area;
    }
    public void setArea(Integer area) {
        this.area = area;
    }
    public Integer getRecommendation() {
        return recommendation;
    }
    public void setRecommendation(Integer recommendation) {
        this.recommendation = recommendation;
    }
    public Integer getFidesGrade() {
        return fidesGrade;
    }
    public void setFidesGrade(Integer fidesGrade) {
        this.fidesGrade = fidesGrade;
    }
    public String getIntroduction() {
        return introduction;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    public String getRefundIntro() {
        return refundIntro;
    }
    public void setRefundIntro(String refundIntro) {
        this.refundIntro = refundIntro;
    }
    public Integer getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }
    public Float getPrepayPrice() {
        return prepayPrice;
    }
    public void setPrepayPrice(Float prepayPrice) {
        this.prepayPrice = prepayPrice;
    }
    public String getPhotoPath() {
        return photoPath;
    }
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
    public int getLatestBuyerCount() {
        return latestBuyerCount;
    }
    public void setLatestBuyerCount(int latestBuyerCount) {
        this.latestBuyerCount = latestBuyerCount;
    }

}

