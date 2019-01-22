package com.example.demo.utils;

import java.util.Random;

/**
 * @author kai.wang
 */
public class YxStringUtils {

    /**
     * 产生一个随机字符串，微信使用
     */
    private static final String alphaBase = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * 一个空格
     */
    private static final String oneSpace = " ";

    private static final String salt = "hnrainyx1111ojsp106";

    /**
     * 产生一个随机字符串
     *
     * @param length
     *            字符串长度
     */
    public static String randomString(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer(length + 1);
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(alphaBase.length());
            sb.append(alphaBase.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 产生一个随机数字串
     *
     * @param length
     *            随机数字串的长度
     */
    public static String randomNumericString(int length) {
        StringBuffer sb = new StringBuffer(length + 1);
        for (int i = 0; i < length; i++) {
            int number = (int) (Math.random() * 10);
            sb.append(number);
        }
        return sb.toString();
    }
}
