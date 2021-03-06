package com.drivingevaluate.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Merchant implements Serializable{
    private int sid;
    private String sname;
    private String stype;
    private String saddress;
    private String snear;
    private String stel;
    private String createdTime;
    private String smembercard;
    private String slevel;
    private String sflag_tuan;
    private String longitude;
    private String latitude;
    private String sintroduction;
    private String sdetails;
    private String stips;
    private String sflag_promise;
    private String photoPath;

    private float marketPrice;
    private float ourPrice;
    private float prePay;

    private Float distance;

    //最近一个月购买人数
    private int buyInMonth;
    //销售总数
    private int sellCount;

    private int spendTime;
    private int zhekou;

    private float item1;
    private float item2;
    private float item3;
    private float item4;
    public float getAvgGrade() {
        return (item1 + item2 + item3) / 3.0f;
    }

    public List<Advertisement> getImgUrls() {
        List<Advertisement> images = new ArrayList<>();
        if(photoPath != null) {
            String[] imgs = photoPath.split(",");
            for (int i = 0; i < imgs.length; i++) {
                images.add(new Advertisement(imgs[i]));
            }
        }
        return images;
    }

    public int getZhekou() {
        return zhekou;
    }

    public void setZhekou(int zhekou) {
        this.zhekou = zhekou;
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

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getSaddress() {
        return saddress;
    }

    public void setSaddress(String saddress) {
        this.saddress = saddress;
    }

    public String getSnear() {
        return snear;
    }

    public void setSnear(String snear) {
        this.snear = snear;
    }

    public String getStel() {
        return stel;
    }

    public void setStel(String stel) {
        this.stel = stel;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getSmembercard() {
        return smembercard;
    }

    public void setSmembercard(String smembercard) {
        this.smembercard = smembercard;
    }

    public String getSlevel() {
        return slevel;
    }

    public void setSlevel(String slevel) {
        this.slevel = slevel;
    }

    public String getSflag_tuan() {
        return sflag_tuan;
    }

    public void setSflag_tuan(String sflag_tuan) {
        this.sflag_tuan = sflag_tuan;
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

    public String getSintroduction() {
        return sintroduction;
    }

    public void setSintroduction(String sintroduction) {
        this.sintroduction = sintroduction;
    }

    public String getSdetails() {
        return sdetails;
    }

    public void setSdetails(String sdetails) {
        this.sdetails = sdetails;
    }

    public String getStips() {
        return stips;
    }

    public void setStips(String stips) {
        this.stips = stips;
    }

    public String getSflag_promise() {
        return sflag_promise;
    }

    public void setSflag_promise(String sflag_promise) {
        this.sflag_promise = sflag_promise;
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

    public int getBuyInMonth() {
        return buyInMonth;
    }

    public void setBuyInMonth(int buyInMonth) {
        this.buyInMonth = buyInMonth;
    }

    public int getSellCount() {
        return sellCount;
    }

    public void setSellCount(int sellCount) {
        this.sellCount = sellCount;
    }

    public int getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(int spendTime) {
        this.spendTime = spendTime;
    }

    public float getItem1() {
        return item1;
    }

    public void setItem1(float item1) {
        this.item1 = item1;
    }

    public float getItem2() {
        return item2;
    }

    public void setItem2(float item2) {
        this.item2 = item2;
    }

    public float getItem3() {
        return item3;
    }

    public void setItem3(float item3) {
        this.item3 = item3;
    }

    public float getItem4() {
        return item4;
    }

    public void setItem4(float item4) {
        this.item4 = item4;
    }
}