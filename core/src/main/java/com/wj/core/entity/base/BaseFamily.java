package com.wj.core.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wj.core.entity.op.OpService;
import com.wj.core.entity.user.SysUserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/*
 * 家庭表
 */
@ApiModel(description = "家庭表")
@Entity
public class BaseFamily {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ApiModelProperty(value = "门牌号")
    private Integer num;
    @ApiModelProperty(value = "所属单元id")
    private Integer unitId;
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @ManyToMany(mappedBy = "familyId")
//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(name = "sys_user_family", joinColumns = {@JoinColumn(name = "familyId", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "userId", referencedColumnName = "id")})
    @JsonIgnore
    private List<SysUserInfo> userId;

    public List<SysUserInfo> getUserId() {
        return userId;
    }

    public void setUserId(List<SysUserInfo> userId) {
        this.userId = userId;
    }

    //    @ManyToMany(mappedBy = "familyId")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "op_family_service", joinColumns = {@JoinColumn(name = "familyId", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "serviceId", referencedColumnName = "id")})
    @JsonIgnore
    private List<OpService> serviceId;

    public List<OpService> getServiceId() {
        return serviceId;
    }

    public void setServiceId(List<OpService> serviceId) {
        this.serviceId = serviceId;
    }


}
