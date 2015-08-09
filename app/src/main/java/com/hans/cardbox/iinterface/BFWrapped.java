package com.hans.cardbox.iinterface;

import java.lang.ref.WeakReference;

/**
 * 项目名称：Bmob_Sample_fast
 * 创建人：开发
 * 创建时间：2015/8/8 15:39
 * 备注：
 */
public class BFWrapped<R> {
    private WeakReference<BF<R>> reference;

    public BFWrapped(BF<R> wrapped) {
        this.reference = new WeakReference<BF<R>>(wrapped);
    }
}
