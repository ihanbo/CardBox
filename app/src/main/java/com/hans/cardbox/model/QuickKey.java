package com.hans.cardbox.model;

import com.hans.mydb.annotation.H_TABLE;

/**
 * Created by hanbo1 on 2015/8/11.
 */
@H_TABLE(name = "QuickKey")
public class QuickKey {

    public String key;
    public String cardID;

    public QuickKey(String key, String cardID) {
        this.key = key;
        this.cardID = cardID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }
}
