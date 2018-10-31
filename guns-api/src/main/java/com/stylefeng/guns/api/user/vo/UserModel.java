package com.stylefeng.guns.api.user.vo;

import java.io.Serializable;

/**
 * @author young
 * @description 用户注册信息
 * @date 2018/10/31 22:13
 **/
public class UserModel implements Serializable {
    private String userName;
    private String password;
    private String email;
    private String phone;
    private String address;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
