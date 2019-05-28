package com.api.entity;


public class BaseUnit {


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getStorey() {
        return storey;
    }

    public void setStorey(String storey) {
        this.storey = storey;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    private int id;
    // 单元号
    private String num;
    // 每个单元共有多少层
    private String storey;
    // 那栋楼的id
    private String floorId;

}
