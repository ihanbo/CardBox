package com.hans.cardbox.tools;

import android.net.Uri;
import android.text.TextUtils;

/**
 * 项目名称：Bmob_Sample_fast
 * 创建人：开发
 * 创建时间：2015/8/8 21:32
 * 备注：
 */
public class UriTools {
    private static  String SCHEME = "card://";

    //用户中心
    public static String HOST_USER_CENTER = "user_center";
    public static String PATH_USER_LOGIN = "/login";
    public static String PATH_USER_REGISTER = "/register";
    public static String PATH_USER_NUM_AUTH = "/num_auth";

    public static Uri getUri(String host,String path){
       return getUri(host,path,null);
    }
    public static Uri getUri(String host,String path,String query){
        StringBuilder sb = new StringBuilder(SCHEME);
        sb.append(host).append(path);
        if(!TextUtils.isEmpty(query)){
            if(!query.startsWith("?")){
                query = "?"+query;
            }
            sb.append(query);
        }
        return Uri.parse(sb.toString());
    }

    public static boolean getBooleanQueryParameter(Uri uri,String key){
        return uri.getBooleanQueryParameter(key,false);
    }
    public static String getQueryParameter(Uri uri,String key){
        return uri.getQueryParameter( key);
    }

}
