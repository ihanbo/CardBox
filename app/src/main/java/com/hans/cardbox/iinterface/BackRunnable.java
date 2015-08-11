package com.hans.cardbox.iinterface;

import android.net.Uri;

import java.lang.ref.WeakReference;

/**
 * Created by hanbo1 on 2015/8/11.
 */
public abstract class BackRunnable implements  Runnable {
    private WeakReference<MessageListener> reference;

    public BackRunnable(MessageListener listener) {
        if(listener==null){
            throw new RuntimeException("MessageListener 为空");
        }
        this.reference = new WeakReference<MessageListener>(listener);
    }


    public boolean notifyMsgIfAvailable(Uri uri){
        MessageListener listener = reference.get();
        if(listener!=null&&listener.isAvaliable()){
            listener.notifyMsg(uri);
            return true;
        }else {
            return false;
        }
    }

}
