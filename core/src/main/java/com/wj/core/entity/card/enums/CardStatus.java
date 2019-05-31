package com.wj.core.entity.card.enums;

/**
 * Created by sun on 2019/5/30.
 */
public enum CardStatus {

    NO("显示"),
    YES("隐藏");

    private final String name;

    CardStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
