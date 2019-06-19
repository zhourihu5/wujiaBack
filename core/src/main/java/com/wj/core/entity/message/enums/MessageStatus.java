package com.wj.core.entity.message.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MessageStatus {


    NO("未读"),
    YES("已读");

    public String getName() {
        return name;
    }

    private final String name;

    MessageStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
