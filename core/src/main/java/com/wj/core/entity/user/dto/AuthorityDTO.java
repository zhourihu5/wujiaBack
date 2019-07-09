package com.wj.core.entity.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import springfox.documentation.spring.web.json.Json;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 权限表
 *
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "权限表")
public class AuthorityDTO {
    @ApiModelProperty(value = "权限ID")
    private Integer id;
    @ApiModelProperty(value = "路由名称")
    private String name;
    @ApiModelProperty(value = "路由地址")
    private String path;
    @ApiModelProperty(value = "父路由ID")
    private Integer pid;
    @ApiModelProperty(value = "是否重定向到某个路由")
    private String redirect;
    @ApiModelProperty(value = "路由对应的前端组件名称或地址")
    private String component;
    @ApiModelProperty(value = "")
    private Object meta;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Object getMeta() {
        return meta;
    }

    public void setMeta(Object meta) {
        this.meta = meta;
    }
}
