package com.wj.core.service.activity;

import com.google.common.collect.Lists;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.activity.BlackList;
import com.wj.core.entity.activity.dto.ActivityUserDTO;
import com.wj.core.entity.address.Address;
import com.wj.core.entity.atta.AttaInfo;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.commodity.Commodity;
import com.wj.core.entity.experience.Experience;
import com.wj.core.entity.order.OrderInfo;
import com.wj.core.entity.task.TaskEntity;
import com.wj.core.entity.user.SysUserInfo;
import com.wj.core.entity.user.dto.XcxLoginDTO;
import com.wj.core.repository.activity.ActivityRepository;
import com.wj.core.repository.activity.BlackListRepository;
import com.wj.core.repository.address.AddressRepository;
import com.wj.core.repository.atta.AttaInfoRepository;
import com.wj.core.repository.commodity.CommodityRepository;
import com.wj.core.repository.order.OrderInfoRepository;
import com.wj.core.repository.user.UserInfoRepository;
import com.wj.core.service.address.AddressService;
import com.wj.core.service.exception.ErrorCode;
import com.wj.core.service.exception.ServiceException;
import com.wj.core.service.job.JobService;
import com.wj.core.service.message.MessageService;
import com.wj.core.util.time.DateFormatUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class BlackListService {

    @Autowired
    private BlackListRepository blackListRepository;

    public Page<BlackList> findAll(Integer couponId, Pageable pageable) {
        Page<BlackList> page = null;
        if (couponId != null) {
            page = blackListRepository.findAllByCouponId(couponId, pageable);
        } else {
            page = blackListRepository.findAll(pageable);
        }
        return page;
    }


    @Transactional
    public void deleteBlackList(Integer couponId) {
        blackListRepository.deleteByCouponId(couponId);
    }
}
