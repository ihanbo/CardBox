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

    private static String getAccountString() {
        String ujs = Prefs.getString(Account.P_KEY, null);
        if (TextUtils.isEmpty(ujs)) {
            return null;
        }
        try {
            return DESUtil.decrypt(ujs, getMasterKey());
        } catch (Exception e) {
            throw new RuntimeException("解密用户数据失败");
        }
    }

    public static void logOut() {
        Prefs.remove(Account.P_KEY);
        Prefs.remove("loghyfs");
        mAccount = null;
    }

    public static Account getLocalAccount() {
        String ujs = getAccountString();
        if (!TextUtils.isEmpty(ujs)) {
            return JsonTools.getObject(ujs, Account.class);
        }
        return null;
    }

    public static void saveAccount(Account account) {
        if (account == null || account.getKey() == null) {
            return;
        }
        mAccount = account;
        String ujs = JsonTools.getJsonString(account);
        try {
            String masterkey = generateMasterkey(account.getPrimaryKey());
            String encrytStr = DESUtil.encrypt(ujs, masterkey);
            Prefs.putString(Account.P_KEY, encrytStr);

            savePrimaryKey(account.getPrimaryKey());
        } catch (Exception e) {
            throw new RuntimeException("加密用户数据失败", e);
        }
    }

    public static Account getCachedAccount() {
        if (mAccount == null) {
            synchronized (UserUtils.class) {
                if (mAccount == null) {
                    mAccount = getLocalAccount();
                }
            }
        }
        return mAccount;
    }

    public static boolean isLogin() {
        return getCachedAccount() == null;
    }

    /**
     * 获取加密的key
     * @return
     */
    public static String getMasterKey() {
        return generateMasterkey(getPrimaryKey());
    }



    private static void savePrimaryKey(String primaryKey) {
        Prefs.putString("loghyfs", primaryKey);
    }

    private static String getPrimaryKey() {
        return Prefs.getString("loghyfs", null);
    }

    /**
     * 生成最终masterkey的方法
     * @param primaryKey 用户的primaryKey
     * @return
     */
    private static String generateMasterkey(String primaryKey) {
        return primaryKey + "2gfds493";
    }

}
