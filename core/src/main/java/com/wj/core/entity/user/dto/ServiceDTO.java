package com.wj.core.entity.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wj.core.entity.base.BaseFamily;
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

    //    @ApiModelProperty(value = "是否订阅 0.否 1.是")
//    private Integer isSubscribe;
//    @ApiModelProperty(value = "用户ID")
//    private Integer userId;
//    @ApiModelProperty(value = "服务ID")
//    private Integer id;
//    @ApiModelProperty(value = "标题")
//    private String title;
//    @ApiModelProperty(value = "剪辑")
//    private String memo;
//    @ApiModelProperty(value = "封面")
//    private String cover;
//    @ApiModelProperty(value = "地址")
//    private String url;
//    @ApiModelProperty(value = "服务类型 1.热门 2.政务")
//    private Integer type;
//    @ApiModelProperty(value = "类别 1.本周 2.人气 3.美食")
//    private Integer category;
//    @ApiModelProperty(value = "标识 1.app 2.链接")
//    private Integer flag;
//    @ApiModelProperty(value = "状态 1.可用 0.不可用")
//    private Integer status;
//    @ApiModelProperty(value = "创建时间")
//    private Date createDate;
//
//    public Integer getIsSubscribe() {
//        return isSubscribe;
//    }
//
//    public void setIsSubscribe(Integer isSubscribe) {
//        this.isSubscribe = isSubscribe;
//    }
//
//    public Integer getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Integer userId) {
//        this.userId = userId;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getMemo() {
//        return memo;
//    }
//
//    public void setMemo(String memo) {
//        this.memo = memo;
//    }
//
//    public String getCover() {
//        return cover;
//    }
//
//    public void setCover(String cover) {
//        this.cover = cover;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public Integer getType() {
//        return type;
//    }
//
//    public void setType(Integer type) {
//        this.type = type;
//    }
//
//    public Integer getFlag() {
//        return flag;
//    }
//
//    public void setFlag(Integer flag) {
//        this.flag = flag;
//    }
//
//    public Integer getStatus() {
//        return status;
//    }
//
//    public void setStatus(Integer status) {
//        this.status = status;
//    }
//
//    public Date getCreateDate() {
//        return createDate;
//    }
//
//    public void setCreateDate(Date createDate) {
//        this.createDate = createDate;
//    }
//
//    public Integer getCategory() {
//        return category;
//    }
//
//    public void setCategory(Integer category) {
//        this.category = category;
//    }

}
