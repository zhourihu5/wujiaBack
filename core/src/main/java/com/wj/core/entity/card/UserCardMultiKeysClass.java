package com.wj.core.entity.card;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserCardMultiKeysClass implements Serializable {

    private Integer userId;
    private Integer cardId;

    //  ***重写hashCode与equals方法***  划重点！
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((userId == null) ? 0 : userId.hashCode());
        result = PRIME * result + ((cardId == null) ? 0 : cardId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final UserCardMultiKeysClass other = (UserCardMultiKeysClass) obj;
        if (userId == null) {
            if (other.userId != null) {
                return false;
            }
        } else if (!userId.equals(other.userId)) {
            return false;
        }
        if (cardId == null) {
            if (other.cardId != null) {
                return false;
            }
        } else if (!cardId.equals(other.cardId)) {
            return false;
        }
        return true;
    }

}
