package com.drivingevaluate.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
    private Integer userId;
    private String account;
    private String userName;
    private String password;
    private String token;
    private String enable;
    private String sex;
    private String ip;

    private Date registerTime;
    private Integer age;
    private Float lat;//纬度
    private Float lon;//经度
    private String sign;

    private Integer grade;//等级
    private String identifyCode;//验证码
    private Date identifyCodeSendTime;//验证码发送时间  用以检查验证码是否过期
    private String headPath;//头像路径

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getIdentifyCode() {
        return identifyCode;
    }

    public void setIdentifyCode(String identifyCode) {
        this.identifyCode = identifyCode;
    }

    public Date getIdentifyCodeSendTime() {
        return identifyCodeSendTime;
    }

    public void setIdentifyCodeSendTime(Date identifyCodeSendTime) {
        this.identifyCodeSendTime = identifyCodeSendTime;
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", account='" + account + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", enable='" + enable + '\'' +
                ", sex='" + sex + '\'' +
                ", ip='" + ip + '\'' +
                ", registerTime=" + registerTime +
                ", age=" + age +
                ", lat=" + lat +
                ", lon=" + lon +
                ", sign='" + sign + '\'' +
                ", grade=" + grade +
                ", identifyCode='" + identifyCode + '\'' +
                ", identifyCodeSendTime=" + identifyCodeSendTime +
                ", headPath='" + headPath + '\'' +
                '}';
    }
}
