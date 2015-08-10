package com.hans.cardbox.iinterface;

import cn.bmob.v3.listener.SaveListener;

/**
 * 项目名称：Bmob_Sample_fast
 * 创建人：开发
 * 创建时间：2015/8/8 20:52
 * 备注：
 */
public abstract  class BombInsertListener<T> extends SaveListener {
    private T t;
    public CallbackAvailableListener mListener;
    public BombInsertListener(CallbackAvailableListener mListener,T t) {
        this.mListener = mListener;
        this.t = t;
    }
    public abstract void onSucced(T t);
    public abstract void onFailed(int code,String msg);
    @Override
    public void onSuccess() {
        if(mListener.isAvaliable()){
            onSucced(t);
        }
    }

    @Override
    public void onFailure(int code,String msg) {
        if(mListener.isAvaliable()){
            onFailed(code,msg);
        }
    }
}
