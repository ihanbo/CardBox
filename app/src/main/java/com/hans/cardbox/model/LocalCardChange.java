package com.hans.cardbox.model;

import com.hans.mydb.annotation.H_TABLE;

/**
 * 项目名称：Bmob_Sample_fast
 * 创建人：开发
 * 创建时间：2015/8/9 22:33
 * 备注：本地Card变动的缓存，同步的时候用
 */
@H_TABLE(name = "local_change")
public class LocalCardChange {
    public static final int TYPE_ADD = 1;
    public static final int TYPE_DELETE = 2;
    public static final int TYPE_UPDATE = 4;


    public String carID;
    public int type;

    public LocalCardChange(String carID) {
        this.carID = carID;
    }

    public LocalCardChange() {
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public LocalCardChange setTypeAdd(){
        type = TYPE_ADD;
        return this;
    }
    public LocalCardChange setTypeUpdate(){
        type = TYPE_UPDATE;
        return this;
    }
    public LocalCardChange setTypeDelete(){
        type = TYPE_DELETE;
        return this;
    }
}
