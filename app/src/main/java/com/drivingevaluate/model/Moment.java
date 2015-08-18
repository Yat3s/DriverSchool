package com.drivingevaluate.model;
public class Moment {
    private int id;
    private double distance;
    private int clicks;
    private int commentCount;
    private int praiseCount;

    private long createTime;
    private long firstReply;
    private String description;
    private String imgIds;
    private String title;

    private String imgPathsLimit;
    private String[] images;

    private User user;

    private boolean praised;

    public boolean isPraised() {
        return praised;
    }

    public long getFirstReply() {
        return firstReply;
    }

    public void setFirstReply(long firstReply) {
        this.firstReply = firstReply;
    }

    public void setPraised(boolean praised) {
        this.praised = praised;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgIds() {
        return imgIds;
    }

    public void setImgIds(String imgIds) {
        this.imgIds = imgIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgPathsLimit() {
        return imgPathsLimit;
    }

    public void setImgPathsLimit(String imgPathsLimit) {
        this.imgPathsLimit = imgPathsLimit;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }
}
