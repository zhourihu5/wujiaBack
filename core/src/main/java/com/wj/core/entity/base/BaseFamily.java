package com.wj.core.entity.base;

import javax.persistence.Entity;

@Entity
public class BaseFamily {

    private Integer id;
    // 门牌号
    private String num;
    // 所属单元id
    private String unitId;

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

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
}
