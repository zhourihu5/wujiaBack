package com.wj.core.entity.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;

/**
 * 权限表
 *
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "权限表")
@Entity
public class SysAuthority {

    @ApiModelProperty(value = "权限ID")
    private Integer id;
    @ApiModelProperty(value = "路由名称")
    private String name;
    @ApiModelProperty(value = "路由标识")
    private String flag;
    @ApiModelProperty(value = "路由地址")
    private String path;
    @ApiModelProperty(value = "父路由ID")
    private Integer pid;
    @ApiModelProperty(value = "路由名称")
    private String title;
    @ApiModelProperty(value = "路由图标")
    private String icon;
    @ApiModelProperty(value = "是否重定向到某个路由")
    private String redirect;
    @ApiModelProperty(value = "路由对应的前端组件名称或地址")
    private String component;

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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

}
