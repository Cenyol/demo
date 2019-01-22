package com.example.demo.utils;

import java.math.BigDecimal;
import java.security.SecureRandom;

/**
 * @author swh
 */
public class NumberUtils {

    /**
     * 获取区间的随机数 [min,max)
     *
     * @param min 最小值
     * @param max 最大值
     */
    public static int randomInt(int min, int max) {
        SecureRandom random = new SecureRandom();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 获取区间的随机数 [0,1)
     */
    public static float random() {
        SecureRandom random = new SecureRandom();
        return random.nextFloat();
    }

    /**
     * 金额从分转换成元
     */
    public static BigDecimal fen2Yuan(long fen) {
        return new BigDecimal(fen).multiply(new BigDecimal("0.01"));
    }

    /**
     * 金额从分转换成元
     */
    public static BigDecimal fen2Yuan(String fen) {
        return new BigDecimal(fen).multiply(new BigDecimal("0.01"));
    }

    /**
     * 金额从元转换成分
     */
    public static long yuan2Fen(String yuanStr) {
        return yuan2Fen(Double.valueOf(yuanStr));
    }

    /**
     * 金额从元转换成分
     */
    public static long yuan2Fen(double yuan) {
        return yuan2Fen(BigDecimal.valueOf(yuan));
    }

    /**
     * 金额从元转换成分
     */
    public static long yuan2Fen(BigDecimal yuan) {
        return yuan.multiply(new BigDecimal(100L)).longValue();
    }

}
