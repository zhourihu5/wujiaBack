package com.wj.core.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wj.core.entity.base.BaseFamily;
import com.wj.core.entity.card.UserCard;
import com.wj.core.entity.op.OpService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 用户表
 *
 * @author thz
 * @version 1.0
 */
@ApiModel(description = "用户表")
@Entity
@Table(name = "sys_user_info")
public class SysUserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "用户ID")
    private Integer id;
    @ApiModelProperty(value = "用户名称")
    private String userName;
    @ApiModelProperty(value = "用户名称")
    private String password;
    @ApiModelProperty(value = "用户名称")
    private String nickName;
    @ApiModelProperty(value = "用户头像")
    private String icon;
    @ApiModelProperty(value = "用户状态")
    private Integer status;
    @ApiModelProperty(value = "标识 1、后台用户 2、ipad用户 3、app用户")
    private Integer flag;
    @ApiModelProperty(value = "创建时间")
    private Date createDate;
    @ApiModelProperty(value = "社区ID")
    @Transient
    private Integer communtityId;
    @ApiModelProperty(value = "家庭ID")
    @Transient
    private Integer fid;
    @ApiModelProperty(value = "1、房产所有人 2、使用人")
    @Transient
    private Integer identity;

    public List<UserCard> getUserCards() {
        return userCards;
    }

    public void setUserCards(List<UserCard> userCards) {
        this.userCards = userCards;
    }

    @OneToMany(mappedBy = "userInfo")
    private List<UserCard> userCards;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCommuntityId() {
        return communtityId;
    }

    public void setCommuntityId(Integer communtityId) {
        this.communtityId = communtityId;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    //因为多对多之间会通过一张中间表来维护两表直接的关系，
    // 所以通过 JoinTable 这个注解来声明，
    // name就是指定了中间表的名字，
    // JoinColumns是一个 @JoinColumn类型的数组，
    // 表示的是我这方在对方中的外键名称，我方是Course，
    // 所以在对方外键的名称就是 rid，inverseJoinColumns也是一个 @JoinColumn类型的数组，
    // 表示的是对方在我这放中的外键名称，对方是Teacher，所以在我方外键的名称就是 tid
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "sys_user_family", joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "familyId", referencedColumnName = "id")})
    @JsonIgnore
    private List<BaseFamily> familyId;

    public List<BaseFamily> getFamilyId() {
        return familyId;
    }

    public void setFamilyId(List<BaseFamily> familyId) {
        this.familyId = familyId;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "op_family_service", joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "serviceId", referencedColumnName = "id")})
    @JsonIgnore
    private List<OpService> serviceId;

    public List<OpService> getServiceId() {
        return serviceId;
    }

    public void setServiceId(List<OpService> serviceId) {
        this.serviceId = serviceId;
    }

}
