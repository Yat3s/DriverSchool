package com.drivingevaluate.model;

/**
 * Created by Yat3s on 8/10/15.
 * Email:hawkoyates@gmail.com
 */
public class CoachComment {
    private long createTime;
    private int goodsId;
    private int id;
    private int userId;
    private String judgeWord;
    private String type;
    private User user;

    private float item1;
    private float item2;
    private float item3;
    private float item4;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getJudgeWord() {
        return judgeWord;
    }

    public void setJudgeWord(String judgeWord) {
        this.judgeWord = judgeWord;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
