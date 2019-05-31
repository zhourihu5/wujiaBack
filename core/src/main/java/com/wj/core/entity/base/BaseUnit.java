package com.wj.core.entity.base;

import javax.persistence.Entity;

@Entity
public class BaseUnit {


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    private Integer id;
    // 单元号
    private String num;
    // 每个单元共有多少层
    private String storey;
    // 那栋楼的id
    private String floorId;

}
