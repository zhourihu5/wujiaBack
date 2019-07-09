package com.wj.admin.utils;

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
     * 判断是否为空
     *
     * @author thz
     */
    public static boolean isNull(String flag) {
        if (flag == null || flag == "") {
            return true;
        } else {
            return false;
        }
    }

    // 阿里天气预报接口地址
    public static String HOST = "https://ali-weather.showapi.com";
    // 阿里天气预报接口名称
    public static String PATH = "/hour24";
    // 获取方式
    public static String METHOD = "GET";
    // 阿里云appcode
    public static String APPCODE = "0ccda9e342a84ffe94da122cf081b1bd";

    // AES key
    public static String AESKEY = "WJ";

}
