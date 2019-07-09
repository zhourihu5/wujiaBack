package com.wj.core.entity.user.enums;

/**
 * 发现类型
 */
public enum UserIdentity {
    Owner("1"),
    Household("2");

    private final String name;

    private UserIdentity(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public static String getName(Integer i) {
        switch (i) {
            case 1:
                return "业主";
        }
        return "住户";
    }


}
