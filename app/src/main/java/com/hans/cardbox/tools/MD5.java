package com.hans.cardbox.tools;

import java.security.MessageDigest;

/**
 * 项目名称：Bmob_Sample_fast
 * 创建人：
 * 创建时间：2015/8/8 17:28
 * 备注：加密
 */
public class MD5 {
    /**
     * 获得MD5加密密码的方法
     */
    public static String getMD5ofStr(String origString) {
        String origMD5 = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] result = md5.digest(origString.getBytes());
            origMD5 = byteArray2HexStr(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return origMD5;
    }
    /**
     * 处理字节数组得到MD5密码的方法
     */
    private static String byteArray2HexStr(byte[] bs) {
        StringBuffer sb = new StringBuffer();
        for (byte b : bs) {
            sb.append(byte2HexStr(b));
        }
        return sb.toString();
    }
    /**
     * 字节标准移位转十六进制方法
     */
    private static String byte2HexStr(byte b) {
        String hexStr = null;
        int n = b;
        if (n < 0) {
            //若需要自定义加密,请修改这个移位算法即可
            n = b & 0x7F + 128;
        }
        hexStr = Integer.toHexString(n / 16) + Integer.toHexString(n % 16);
        return hexStr.toUpperCase();
    }
    /**
     * 密码验证方法
     */
    public static boolean verifyPassword(String inputStr, String MD5Code) {
        return getMD5ofStr(inputStr).equals(MD5Code);
    }

}
