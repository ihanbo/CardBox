package com.hans.cardbox.tools;


import android.util.Log;

/**
 * 项目名称：Bmob_Sample_fast
 * 创建人：开发
 * 创建时间：2015/8/8 15:30
 * 备注：
 */
public class LG {

    private static  boolean debug = true;

    public static void i(String tag,String msg){
        if(debug){
            Log.i(tag,msg);
        }
    }
    public static void e(String tag,String msg){
        if(debug){
            Log.e(tag,msg);
        }
    }
    public static void w(String tag,String msg){
        if(debug){
            Log.w(tag,msg);
        }
    }
    public static void d(String tag,String msg){
        if(debug){
            Log.d(tag,msg);
        }
    }


    public static void wc(String msg){
        if(debug){
            Log.w("ww",msg);
        }
    }
}
