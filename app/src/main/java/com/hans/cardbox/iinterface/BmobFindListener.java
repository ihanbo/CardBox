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
public abstract class BmobFindListener<T> extends FindListener<T> {
    public CallbackAvailableListener mListener;


    public BmobFindListener(CallbackAvailableListener mListener) {
        this.mListener = mListener;
    }

    public abstract void onSucced(List<T> list);
    public abstract void onFailed(int code,String msg);
    @Override
    public void onSuccess(List<T> list) {
            if(mListener.isAvaliable()){
                onSucced(list);
            }else{
                LG.wc("BmobFindListener Fragment不可用了");
            }
    }
    @Override
    public void onError(int code,String msg){
        if(mListener.isAvaliable()){
            onFailed(code, msg);
        }
    }
}
