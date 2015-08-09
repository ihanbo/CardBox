package com.hans.cardbox.iinterface;

import com.hans.cardbox.tools.LG;
import com.hans.cardbox.tools.ToastUtil;

import java.util.List;

import cn.bmob.v3.listener.FindListener;

/**
 * 项目名称：Bmob_Sample_fast
 * 创建人：开发
 * 创建时间：2015/8/8 17:43
 * 备注：Bmob查找回调
 */
public abstract class BmobFindListener<T> extends BombListener implements FindListener<T> {


    public BmobFindListener(CallbackAvailableListener mListener) {
        super(mListener);
    }

    public abstract void onSuccessed(List<T> list);
    public abstract void onFailed(String s);
    @Override
    public void onSuccess(List<T> list) {
            if(mListener.isAvaliable()){
                onSuccessed(list);
            }else{
                LG.wc("BmobFindListener Fragment不可用了");
            }
    }
    @Override
    public void onError(String s) {
        if(mListener.isAvaliable()){
            onFailed(s);
        }
    }
}
