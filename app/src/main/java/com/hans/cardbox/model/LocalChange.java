package com.hans.cardbox.model;

import com.hans.mydb.annotation.H_TABLE;

/**
 * 项目名称：Bmob_Sample_fast
 * 创建人：开发
 * 创建时间：2015/8/9 22:33
 * 备注：本地变动的缓存，同步的时候用
 */
@H_TABLE(name = "local_change")
public class LocalChange {
    public static final int TYPE_ADD = 1;
    public static final int TYPE_DELETE = 2;
    public static final int TYPE_UPDATE = 4;


    public String tableName;
    public String pk;
    public int type;

    public LocalChange(String tableName, String pk) {
        this.tableName = tableName;
        this.pk = pk;
    }

    public LocalChange() {
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public LocalChange setTypeAdd(){
        type = TYPE_ADD;
        return this;
    }
    public LocalChange setTypeUpdate(){
        type = TYPE_UPDATE;
        return this;
    }
    public LocalChange setTypeDelete(){
        type = TYPE_DELETE;
        return this;
    }
}
