package com.hans.cardbox.model;

import com.hans.mydb.annotation.APK;
import com.hans.mydb.annotation.H_NotShoot;
import com.hans.mydb.annotation.H_TABLE;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 项目名称：Bmob_Sample_fast
 * 创建人：开发
 * 创建时间：2015/8/8 23:17
 * 备注：
 */
@H_TABLE(name = "Card")
public class Card extends BmobObject {

    @APK
    public String cardID;
    public String name;
    public String icon;
    public  int type;
    @H_NotShoot
    public  List<KeyPass> data;
    public String desc;

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<KeyPass> getData() {
        return data;
    }

    public void setData(List<KeyPass> data) {
        this.data = data;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
