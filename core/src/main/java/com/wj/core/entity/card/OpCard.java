package com.wj.core.entity.card;

import com.wj.core.entity.card.enums.CardType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sun on 2019/5/30.
 */
@Entity
public class OpCard {

    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String memo;
    @Enumerated(EnumType.ORDINAL)
    private CardType type;
    private String url;
    private String icon;
    private Short location;
    private Date createDate;
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Short getLocation() {
        return location;
    }

    public void setLocation(Short location) {
        this.location = location;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
