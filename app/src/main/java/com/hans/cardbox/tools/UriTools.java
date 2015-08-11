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

    //消息提醒
    public static String HOST_MESSAGE = "host_message";
    public static String PATH_ASYNC_NOTIFY = "/async";
    public static String PATH_PROMPT= "/prompt";

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

    /**
     * 从query获取boolean数据
     * @param uri
     * @param key
     * @return
     */
    public static boolean getBooleanQueryParameter(Uri uri,String key){
        return uri.getBooleanQueryParameter(key, false);
    }

    /**
     * 从query获取数据
     * @param uri
     * @param key
     * @return
     */
    public static String getQueryParameter(Uri uri,String key){
        return uri.getQueryParameter(key);
    }




    /**
     * 获取异步消息提醒的Uri
     * @param succ 是否成功
     * @param msg 消息内容
     * @param query 额外的数据
     * @return
     */
    public static Uri getAsyncMsgUri(boolean succ,String msg,String query){
        String qy = "succ="+succ+"&msg="+msg;
        if(!TextUtils.isEmpty(query)){
            qy+=query;
        }
        return getUri(HOST_MESSAGE, PATH_ASYNC_NOTIFY, qy);
    }

    /**
     * 消息提示Uri
     * @param msg
     * @return
     */
    public static Uri getPromptUri(String msg){
        String qy = "prompt="+msg;
        if(TextUtils.isEmpty(msg)){
            return null;
        }
        return getUri(HOST_MESSAGE, PATH_PROMPT, qy);
    }

}
