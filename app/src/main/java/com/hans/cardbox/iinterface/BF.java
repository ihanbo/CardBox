package com.hans.cardbox.iinterface;

/**
 * 项目名称：Bmob_Sample_fast
 * 创建人：开发
 * 创建时间：2015/8/8 15:37
 * 备注：
 */
public interface BF<R> {
    public R onBack();
    public void inFront(R result);
}
