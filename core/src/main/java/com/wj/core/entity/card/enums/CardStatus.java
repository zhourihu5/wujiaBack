package com.wj.core.entity.card.enums;

/**
 * Created by sun on 2019/5/30.
 */
public enum CardStatus {

    NO("隐藏"),
    YES("显示");

    private final String name;

    CardStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
