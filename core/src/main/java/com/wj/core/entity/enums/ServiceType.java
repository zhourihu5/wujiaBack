package com.wj.core.entity.enums;

/**
 *
 */
public enum ServiceType {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4);

    private final Integer id;

    private ServiceType(Integer id) {
        this.id = id;
    }

    public Integer toInt() {
        return id;
    }

    public static String getName(Integer i) {
        switch (i) {
            case 1:
                return "我的服务";
            case 2:
                return "发现";
            case 3:
                return "政务服务";
            case 4:
                return "全部服务";
        }
        return "我的服务";
    }


}
