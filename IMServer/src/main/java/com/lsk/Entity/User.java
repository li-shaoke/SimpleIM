package com.lsk.Entity;

/**
 * Created by lsk10238 on 2017/11/20.
 */
public class User {
    private Integer id;
    private String username;
    private String password;
    private String sex;
    private String educational;
    private String hobbies;
    private String remark;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEducational() {
        return educational;
    }
    public void setEducational(String educational) {
        this.educational = educational;
    }
    public String getHobbies() {
        return hobbies;
    }
    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id=id;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
}
