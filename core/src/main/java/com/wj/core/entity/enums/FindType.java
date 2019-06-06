package com.wj.core.entity.enums;

/**
 * 发现类型
 */
public enum FindType {
    ONE("1"),
    TWO("2");

    private final String name;

    private FindType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public static String getName(Integer i) {
        switch (i) {
            case 1:
                return "本周精选";
            case 2:
                return "人气热门";
        }
        return "其他";
    }


}
