package com.drivingevaluate.model;


public class Coach {
    private int coachId;
    private int dSchoolId;
    private String name;
    private int studyingAmount;//正在学习的学生数量
    private int studentAmount;//总共学习过的学生数量
    private Float score;//评分
    private String introduce;//教练自我简介
    private String tel;//电话
    private String photoPath;//教练头像
    private String classType;//上的班类型
    public int getCoachId() {
        return coachId;
    }
    public void setCoachId(int coachId) {
        this.coachId = coachId;
    }
    public int getdSchoolId() {
        return dSchoolId;
    }
    public void setdSchoolId(int dSchoolId) {
        this.dSchoolId = dSchoolId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getStudyingAmount() {
        return studyingAmount;
    }
    public void setStudyingAmount(int studyingAmount) {
        this.studyingAmount = studyingAmount;
    }
    public int getStudentAmount() {
        return studentAmount;
    }
    public void setStudentAmount(int studentAmount) {
        this.studentAmount = studentAmount;
    }
    public Float getScore() {
        return score;
    }
    public void setScore(Float score) {
        this.score = score;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPhotoPath() {
        return photoPath;
    }
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
    public String getClassType() {
        return classType;
    }
    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
