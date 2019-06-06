package com.wj.core.entity.enums;

/**
 *
 */
public enum AllServiceType {
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    ELEVEN("11"),
    TWELVE("12");

    private final String name;

    private AllServiceType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public static String getName(Integer i) {
        switch (i) {
            case 1:
                return "美食";
            case 2:
                return "玩乐";
            case 3:
                return "生活服务";
            case 4:
                return "理想家具";
            case 5:
                return "亲自活动";
            case 6:
                return "周边游";
            case 7:
                return "拼团/闲置交易";
            case 8:
                return "民宿/租售";
            case 9:
                return "教育";
            case 10:
                return "宠物";
            case 11:
                return "休闲放松";
            case 12:
                return "其他";
        }
        return "其他";
    }


}
