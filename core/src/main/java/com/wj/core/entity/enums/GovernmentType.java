package com.wj.core.entity.enums;

/**
 * 政务服务类型
 */
public enum GovernmentType {
    ONE("1"),
    TWO("2");

    private final String name;

    private GovernmentType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public static String getName(Integer i) {
        switch (i) {
            case 1:
                return "官方部门";
            case 2:
                return "权威媒体";
        }
        return "其他";
    }


}
