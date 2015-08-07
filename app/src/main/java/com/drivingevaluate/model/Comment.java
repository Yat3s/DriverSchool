package com.drivingevaluate.model;
public class Comment {
    private int id;
    private String information;
    private String date;
    private int authorID;
    private String authorName;
    private String authorPic;
    private String picturePath;
    private String style;
    private int styleID;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getInformation() {
        return information;
    }
    public void setInformation(String information) {
        this.information = information;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getAuthorID() {
        return authorID;
    }
    public void setAuthorID(int authorID) {
        this.authorID = authorID;
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
    public String getPicturePath() {
        return picturePath;
    }
    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
    public String getStyle() {
        return style;
    }
    public void setStyle(String style) {
        this.style = style;
    }
    public int getStyleID() {
        return styleID;
    }
    public void setStyleID(int styleID) {
        this.styleID = styleID;
    }
}
