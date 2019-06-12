package com.wj.core.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

/*
 * 社区表
 */
@ApiModel(description = "社区表")
@Entity
@Table(name = "base_communtity")
public class BaseCommuntity {
    @Id
    @GeneratedValue
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer id;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "省编号")
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer province;
    @ApiModelProperty(value = "市编号")
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer city;
    @ApiModelProperty(value = "区编号")
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer area;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(name = "sys_family_communtity", joinColumns = {@JoinColumn(name = "communtityId", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "familyId", referencedColumnName = "id")})
    // 所在社区
//    @OneToOne(mappedBy = "communtityId", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
//    @JsonIgnore
//    @OneToOne(cascade=CascadeType.ALL)//family是关系的维护端
//    @JoinTable(name="sys_family_communtity",joinColumns=@JoinColumn(name="communtityId"),inverseJoinColumns=@JoinColumn(name="familyId"))
//    private BaseFamily familyId;
//
//    public BaseFamily getFamilyId() {
//        return familyId;
//    }
//
//    public void setFamilyId(BaseFamily familyId) {
//        this.familyId = familyId;
//    }
}
