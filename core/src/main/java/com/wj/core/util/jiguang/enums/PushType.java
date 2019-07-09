package com.wj.core.util.jiguang.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum  PushType {

    SYS("系统升级"),
    CARD("卡片"),
    ADV("广告"),
    MSG("消息");

    @JsonValue
    public String getName() {
        return name;
    }

    private final String name;

    PushType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

}
