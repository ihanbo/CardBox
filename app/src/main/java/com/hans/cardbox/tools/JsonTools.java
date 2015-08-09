package com.hans.cardbox.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * 项目名称：Bmob_Sample_fast
 * 创建人：开发
 * 创建时间：2015/8/8 19:19
 * 备注：
 */
public class JsonTools {

//    private static  JSON

    public static String getJsonString(Object obj){
        if(obj==null){
            return null;
        }
        return JSON.toJSONString(obj);
    }

    public static<T> T getObject(String jsonString,Class<T> clazz){
        T t = JSON.parseObject(jsonString,clazz);
        return t;
    }


    public static<T> T getObject(String jsonString,TypeReference<T> type){
        T t = JSON.parseObject(jsonString,type);
        return t;
    }

}
