package com.wj.core.util.bigdecimal;

import java.math.BigDecimal;

public class BigDecimalUtil {
    //等于
    public static boolean eq(BigDecimal a, BigDecimal b){
        return a.compareTo(b) == 0;
    }

    //大于
    public static boolean gt(BigDecimal a, BigDecimal b){
        return a.compareTo(b) > 0;
    }

    //大于0
    public static boolean gt0(BigDecimal a){
        return a.compareTo(BigDecimal.ZERO) > 0;
    }

    //大于等于
    public static boolean get(BigDecimal a, BigDecimal b){
        return a.compareTo(b) >= 0;
    }

    //大于等于0
    public static boolean get0(BigDecimal a){
        return a.compareTo(BigDecimal.ZERO) >= 0;
    }

    //小于
    public static boolean lt(BigDecimal a, BigDecimal b){
        return a.compareTo(b) < 0;
    }

    //小于0
    public static boolean lt0(BigDecimal a){
        return a.compareTo(BigDecimal.ZERO) < 0;
    }

    //小于等于
    public static boolean let(BigDecimal a, BigDecimal b){
        return a.compareTo(b) <= 0;
    }

    //小于等于0
    public static boolean let0(BigDecimal a){
        return a.compareTo(BigDecimal.ZERO) <= 0;
    }
}
