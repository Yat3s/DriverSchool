package com.drivingevaluate.model;

/**
 * Created by Yat3s on 8/16/15.
 * Email:hawkoyates@gmail.com
 */
public class LuckyMoney {
    private int userId;
    private String account;
    private int hongbao;
    private int balance;
    private long createdTime;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getHongbao() {
        return hongbao;
    }

    public void setHongbao(int hongbao) {
        this.hongbao = hongbao;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }
}
