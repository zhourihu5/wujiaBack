package com.api.entity;


public class BaseFloor {


    private int id;
    private String name;
    // 楼号
    private String num;
    // 共有多少个单元
    private String unit;
    // 社区id
    private String communtityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCommuntityId() {
        return communtityId;
    }

    public void setCommuntityId(String communtityId) {
        this.communtityId = communtityId;
    }
}
