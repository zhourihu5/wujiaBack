package com.wj.core.util.base;

import org.apache.commons.lang.StringUtils;

public class CommunityUtil {

    public static String genCommCode(Integer id) {
        return StringUtils.rightPad(String.valueOf(id), 8, "0");
    }

    public static String genCode(String code, Integer count) {
        return code.concat(StringUtils.leftPad(String.valueOf(count), 2, "0"));
    }

    // 是否有期
    public static boolean isIssue(String commCode) {
        return false;
    }

    // 是否有区
    public static boolean isDistrict(String commCode) {
        return false;
    }

    public static void main(String[] args) {
        System.out.println(genCommCode(23));
    }

}
