package com.w.bean.user;


import java.sql.Date;

public class UserInfoBean {
    private int id;
    private int userId;
    private String cellphone;
    private int sex;
    private Date birth;
    private String intro;

    //表示性别，引入辅助变量
    private String sexName;

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

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "id=" + id +
                ", userId=" + userId +
                ", cellphone='" + cellphone + '\'' +
                ", sex=" + sex +
                ", birth=" + birth +
                ", intro='" + intro + '\'' +
                ", sexName='" + sexName + '\'' +
                '}';
    }
}
