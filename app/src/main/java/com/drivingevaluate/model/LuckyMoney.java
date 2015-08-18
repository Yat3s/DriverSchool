package com.drivingevaluate.model;

/**
 * Created by Yat3s on 8/16/15.
 * Email:hawkoyates@gmail.com
 */
public class LuckyMoney {
    private int id;
    private String name;
    private String time;
    private int sumOfMoney;

    public LuckyMoney(String name, String time, int sumOfMoney) {
        this.name = name;
        this.time = time;
        this.sumOfMoney = sumOfMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSumOfMoney() {
        return sumOfMoney;
    }

    public void setSumOfMoney(int sumOfMoney) {
        this.sumOfMoney = sumOfMoney;
    }
}
