package com.hans.cardbox.iinterface;

import java.util.List;

import cn.bmob.v3.listener.InsertListener;

/**
 * 项目名称：Bmob_Sample_fast
 * 创建人：开发
 * 创建时间：2015/8/8 20:52
 * 备注：
 */
public abstract  class BombInsertListener<T> extends BombListener implements InsertListener {
    private T t;
    public BombInsertListener(CallbackAvailableListener mListener,T t) {
        super(mListener);
        this.t = t;
    }
    public abstract void onSuccessed(T t);
    public abstract void onFailed(String s);
    @Override
    public void onSuccess() {
        if(mListener.isAvaliable()){
            onSuccessed(t);
        }
    }

    @Override
    public void onFailure(String s) {
        if(mListener.isAvaliable()){
            onFailed(s);
        }
    }
}
