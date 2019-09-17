package com.wj.core.service.shop;

import com.google.common.collect.Lists;
import com.wj.core.entity.shop.Shop;
import com.wj.core.repository.shop.ShopAddressRepository;
import com.wj.core.repository.shop.ShopRepository;
import com.wj.core.util.time.ClockUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.List;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    public void save(Shop shop) {
        if (shop.getId() == null) {
            shop.setCreateDate(ClockUtil.currentDate());
            shop.setCode(String.valueOf(ClockUtil.currentTimeMillis()));
        }
        shopRepository.save(shop);
    }


    public Page<Shop> getList(Integer pageNum, Integer pageSize, String shopName) {
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = Lists.newArrayList();
            if (StringUtils.isNotBlank(shopName)) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + shopName + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pageable page = PageRequest.of(pageNum - 1, pageSize, Sort.Direction.DESC, "id");
        Page<Shop> pageShop = shopRepository.findAll(specification, page);
        return pageShop;
    }


}
