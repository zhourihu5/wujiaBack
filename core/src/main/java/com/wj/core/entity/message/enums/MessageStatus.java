package com.wj.core.entity.message.enums;

public enum MessageStatus {


    NO("未读"),
    YES("已读");

    private final String name;

    MessageStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
