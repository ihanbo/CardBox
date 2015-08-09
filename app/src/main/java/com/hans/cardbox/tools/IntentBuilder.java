package com.hans.cardbox.tools;

import android.content.Context;
import android.content.Intent;

/**
 * 项目名称：Bmob_Sample_fast
 * 创建人：开发
 * 创建时间：2015/8/8 22:39
 * 备注：
 */
public class IntentBuilder {
    Intent intent;
    public IntentBuilder() {
        intent = new Intent();
    }
    public IntentBuilder(Context context,Class clazz) {
        intent = new Intent(context,clazz);
    }

    public static IntentBuilder create(){
        return new IntentBuilder();
    }
    public static IntentBuilder create(Context context,Class clazz){
        return new IntentBuilder(context,clazz);
    }

    public Intent build(){
        return intent;
    }
}
