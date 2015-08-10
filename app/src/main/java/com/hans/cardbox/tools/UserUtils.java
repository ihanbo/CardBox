package com.hans.cardbox.tools;

import android.text.TextUtils;

import com.hans.cardbox.model.Account;

/**
 * 项目名称：Bmob_Sample_fast
 * 创建人：开发
 * 创建时间：2015/8/8 19:47
 * 备注：用户工具类
 */
public class UserUtils {
    private static Account mAccount;

    private static String getAccountString(){
        String ujs = Prefs.getString(Account.P_KEY,null);
        if(TextUtils.isEmpty(ujs)){
            return null;
        }
        try {
            return DESUtil.decrypt(ujs,getMasterKey());
        } catch (Exception e) {
            throw new RuntimeException("解密用户数据失败");
        }
    }

    public static void logOut(){
        Prefs.remove(Account.P_KEY);
        Prefs.remove("loghyfs");
        Prefs.remove("auth_key");
        mAccount = null;
    }

    public static Account getLocalAccount(){
        String ujs = getAccountString();
        if(!TextUtils.isEmpty(ujs)){
            return JsonTools.getObject(ujs,Account.class);
        }
        return null;
    }

    public static void saveAccount(Account account){
        if(account==null||account.getKey()==null){
            return;
        }
        mAccount = account;
        String ujs =  JsonTools.getJsonString(account);
        try {
            String encrytStr = DESUtil.encrypt(ujs,getMasterKey());
            Prefs.putString(Account.P_KEY, encrytStr);
            saveMasterKey(account.getPrimaryKey());
            saveAuthKey(account.getAuthKey());
        } catch (Exception e) {
            throw new RuntimeException("加密用户数据失败");
        }
    }

    public static Account getCachedAccount(){
        if(mAccount==null){
            synchronized (UserUtils.class){
                if(mAccount==null){
                    mAccount = getLocalAccount();
                }
            }
        }
        return mAccount;
    }

    public static boolean isLogin(){
        return getCachedAccount()==null;
    }


    /**
     * 加密的key
     * @return
     */
    public static String getMasterKey(){
        return Prefs.getString("loghyfs",null);
    }
    /**
     * 加密的key
     * @return
     */
    public static void saveMasterKey(String primaryKey){
        Prefs.putString("loghyfs",primaryKey);
    }

    /**
     * auth的key
     * @return
     */
    public static String getAuthKey(){
        return Prefs.getString("auth_key",null);
    }
    /**
     * auth的key
     * @return
     */
    public static void saveAuthKey(String authKey){
        Prefs.putString("auth_key",authKey);
    }

}
