package com.hans.cardbox.model;


import cn.bmob.v3.BmobObject;

public class Account extends BmobObject {

    public static final String P_KEY = "my_account";
    public String key;
    public String password;
    public String name;
    public String mobile;
    /**
     * 4位验证密码
     */
    public String authKey;

    public String token;
    public String primaryKey;

    public Account(String key, String password, String name, String mobile, String authKey, String token, String primaryKey) {
        this.key = key;
        this.password = password;
        this.name = name;
        this.mobile = mobile;
        this.authKey = authKey;
        this.token = token;
        this.primaryKey = primaryKey;
    }

    public Account() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }
}
