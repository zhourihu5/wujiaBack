package com.wj.core.entity.activity.dto;

import com.wj.core.entity.activity.CouponCode;
import lombok.Data;

import java.util.List;

@Data
public class CouponMessageDTO {
    CouponCode couponCode;
    Boolean flag;
}
