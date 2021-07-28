package com.banyuan.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SecurityUtils {

    /**
     * 对字符串进行md5加密, 并将加密之后的内容转成字符串
     * @param str
     * @return
     */
    public static String md5(String str) {

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] bs = messageDigest.digest(str.getBytes());
        String newStr = Base64.getEncoder().encodeToString(bs);
        return newStr;
    }
}
