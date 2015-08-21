package com.drivingevaluate.model;

/**
 * Created by Yat3s on 8/19/15.
 * Email:hawkoyates@gmail.com
 */
public class Image {
    private int id;
    private String url;
    private String imgPath;
    private String imgId;

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
