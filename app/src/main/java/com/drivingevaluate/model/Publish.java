package com.drivingevaluate.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.drivingevaluate.util.DateUtils;

public class Publish{

    private Integer id;
    private String title;
    private String description;
    private String createTime;//创建时间
    private Integer clicks;//点击量
    private Integer type;//分类
    private Float dist;//与用户距离
    private String imgIds;//所有图片id
    private String imgPathsLimit;//图片路径
    //private String imgPathFull;//图片路径
    private Date lastReply;//最后回复
    private Date firstReply;//首次回复
    private String pubAddr;
    private Double longitude;
    private Double latitude;
    private int cityNo;
    private int districtNo;

    private User user;//发布人
    private List<ImageRecord> images;//所有图片

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getClicks() {
        return clicks;
    }

    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Float getDist() {
        return dist;
    }

    public void setDist(Float dist) {
        this.dist = dist;
    }

    public String getImgIds() {
        return imgIds;
    }

    public void setImgIds(String imgIds) {
        this.imgIds = imgIds;
    }

    public String getImgPathsLimit() {
        return imgPathsLimit;
    }

    public void setImgPathsLimit(String imgPathsLimit) {
        this.imgPathsLimit = imgPathsLimit;
    }

    public Date getLastReply() {
        return lastReply;
    }

    public void setLastReply(Date lastReply) {
        this.lastReply = lastReply;
    }

    public Date getFirstReply() {
        return firstReply;
    }

    public void setFirstReply(Date firstReply) {
        this.firstReply = firstReply;
    }

    public List<ImageRecord> getImages() {
        return images;
    }

    public void setImages(List<ImageRecord> images) {
        this.images = images;
    }

    public String getPubAddr() {
        return pubAddr;
    }

    public void setPubAddr(String pubAddr) {
        this.pubAddr = pubAddr;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public int getCityNo() {
        return cityNo;
    }

    public void setCityNo(int cityNo) {
        this.cityNo = cityNo;
    }

    public int getDistrictNo() {
        return districtNo;
    }

    public void setDistrictNo(int districtNo) {
        this.districtNo = districtNo;
    }

}
