package com.drivingevaluate.model;

public class UserAccount {

    private Integer id;

    private Integer userId;

    private Float balance;

    private String zhifuPwd;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public String getZhifuPwd() {
        return zhifuPwd;
    }

    public void setZhifuPwd(String zhifuPwd) {
        this.zhifuPwd = zhifuPwd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
