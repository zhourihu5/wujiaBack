package com.wj.core.entity.card.enums;

/**
 * Created by sun on 2019/5/30.
 */
public enum CardType {

    OP("功能"),
    WU("外链"),
    IU("内链"),
    IMG("图文");

    private final String name;

    CardType( String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
