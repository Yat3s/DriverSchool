package com.drivingevaluate.model;
public class Moment {
    private int id;
    private double distance;
    private String locate;
    private String authorName;
    private String authorPic;
    private int commentAmount=0;
    private int audienceAmount=0;
    private int likeAmount=0;
    private String GPS="";
    private String publicTime;
    private int authorID;
    private String picturePath;
    private String content;
    private String like;
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
    public String getLocate() {
        return locate;
    }
    public void setLocate(String locate) {
        this.locate = locate;
    }
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String getAuthorPic() {
        return authorPic;
    }
    public void setAuthorPic(String authorPic) {
        this.authorPic = authorPic;
    }
    public int getCommentAmount() {
        return commentAmount;
    }
    public void setCommentAmount(int commentAmount) {
        this.commentAmount = commentAmount;
    }
    public int getAudienceAmount() {
        return audienceAmount;
    }
    public void setAudienceAmount(int audienceAmount) {
        this.audienceAmount = audienceAmount;
    }
    public int getLikeAmount() {
        return likeAmount;
    }
    public void setLikeAmount(int likeAmount) {
        this.likeAmount = likeAmount;
    }
    public String getGPS() {
        return GPS;
    }
    public void setGPS(String gPS) {
        GPS = gPS;
    }
    public String getPublicTime() {
        return publicTime;
    }
    public void setPublicTime(String publicTime) {
        this.publicTime = publicTime;
    }
    public int getAuthorID() {
        return authorID;
    }
    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }
    public String getPicturePath() {
        return picturePath;
    }
    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getLike() {
        return like;
    }
    public void setLike(String like) {
        this.like = like;
    }
}
