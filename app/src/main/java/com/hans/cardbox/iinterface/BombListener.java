package com.hans.cardbox.iinterface;

/**
 * 项目名称：Bmob_Sample_fast
 * 创建人：开发
 * 创建时间：2015/8/8 20:52
 * 备注：
 */
public abstract  class BombListener {
    public CallbackAvailableListener mListener;

    public BombListener(CallbackAvailableListener mListener) {
        this.mListener = mListener;
    }

    public abstract void onSucced();

    public abstract void onFailed(int caode, String msg);

}
