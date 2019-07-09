package com.wj.core.entity.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.op.OpBanner;
import com.wj.core.entity.op.OpService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 服务表
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "服务表")
public class ServiceDTO {

    @ApiModelProperty(value = "类型ID")
    private Integer typeId;
    @ApiModelProperty(value = "类型名称")
    private String typeName;
    @ApiModelProperty(value = "服务列表")
    private List<OpService> list;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<OpService> getList() {
        return list;
    }

    public void setList(List<OpService> list) {
        this.list = list;
    }

}
