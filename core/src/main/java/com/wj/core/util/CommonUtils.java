package com.wj.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Random;

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

    /**
     * 判断是否为空
     *
     * @author thz
     */
    public static String randomCode() {
        String randomCode = "";
        String model = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        char[] m = model.toCharArray();
        for (int i = 0; i < 6; i++) {
            char c = m[(int) (Math.random() * 62)];
            // 保证六位随机数之间没有重复的
//            if (randomCode.contains(String.valueOf(c))) {
//                i--;
//                continue;
//            }
            randomCode = randomCode + c;
        }
        return randomCode;
    }

    //获取随机字符串
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static boolean isDateBefore(String date1, String date2) {
        try {
            DateFormat df = DateFormat.getDateTimeInstance();
            return df.parse(date1).before(df.parse(date2));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean isDateAfter(String date1, String date2) {
        try {
            DateFormat df = DateFormat.getDateTimeInstance();
            return df.parse(date1).after(df.parse(date2));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
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
