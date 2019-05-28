package com.api.utils;

/**
 * 工具类
 *
 * @author thz
 */
public class CommonUtils {
    /**
     * 生成6位随机数
     *
     * @author thz
     */
    public static String createRandomNum(int i) {
        int random = (int) ((Math.random() * 9 + 1) * 100000);
        return String.valueOf(random);
    }

    /**
     * 阿里的key
     *
     * @author thz
     */
    public static String ALIKEY = "";
}
