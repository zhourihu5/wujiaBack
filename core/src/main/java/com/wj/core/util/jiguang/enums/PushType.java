package com.wj.core.util.jiguang.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum  PushType {

    SYS("系统推送"),
    BUS("业务推送"),
    MSG("消息通知");

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
