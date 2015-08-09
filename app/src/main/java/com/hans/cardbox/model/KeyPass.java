package com.hans.cardbox.model;

import com.hans.mydb.annotation.H_TABLE;

import cn.bmob.v3.BmobObject;

/**
 * 项目名称：Bmob_Sample_fast
 * 创建人：开发
 * 创建时间：2015/8/9 22:21
 * 备注：
 */
@H_TABLE(name = "KeyPass")
public class KeyPass extends BmobObject{
    public String key;
    public String pass;
    public String desc;
    public String cardID;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }
}
