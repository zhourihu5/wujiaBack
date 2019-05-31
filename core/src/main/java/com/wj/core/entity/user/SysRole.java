package com.wj.core.entity.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 用户角色表
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "用户角色表")
@Entity
public class SysRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "名称")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
