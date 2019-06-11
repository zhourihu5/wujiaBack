package com.wj.core.entity.message.enums;

public enum MessageType {


    SYS("系统通知"),
    WY("物业通知"),
    SQ("社区通知");

    private final String name;

    MessageType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
