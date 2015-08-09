package com.hans.cardbox.tools;

import android.text.TextUtils;

import com.hans.cardbox.model.Account;

/**
 * 项目名称：Bmob_Sample_fast
 * 创建人：开发
 * 创建时间：2015/8/8 19:47
 * 备注：
 */
public class UserUtils {
    private static Account mAccount;

    public static String getAccountString(){
        String ujs = Prefs.getString(Account.P_KEY,null);
        if(TextUtils.isEmpty(ujs)){
            return null;
        }
        return ujs;
    }

    public static Account getAccount(){
        String ujs = Prefs.getString(Account.P_KEY,null);
        if(TextUtils.isEmpty(ujs)){
            return null;
        }
        return JsonTools.getObject(ujs,Account.class);
    }

    public static void saveAccount(Account account){
        if(account==null||account.getKey()==null){
            return;
        }
        mAccount = account;
        String ujs =  JsonTools.getJsonString(account);
        Prefs.putString(Account.P_KEY,ujs);
    }




    public static Account getCachedAccount(){
        if(mAccount==null){
            synchronized (UserUtils.class){
                if(mAccount==null){
                    String ujs = Prefs.getString(Account.P_KEY,null);
                    if(!TextUtils.isEmpty(ujs)){
                        mAccount = JsonTools.getObject(ujs,Account.class);
                    }
                }
            }
        }
        return mAccount;
    }

    public static boolean isLogin(){
        return getCachedAccount()==null;
    }

    public static void logOut(){
        Prefs.remove(Account.P_KEY);
        mAccount = null;
    }

    public static String getMasterKey(){
        return getCachedAccount().getPrimaryKey()+getCachedAccount().getToken();
    }

}
