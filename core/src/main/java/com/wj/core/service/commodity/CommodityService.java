package com.wj.core.service.commodity;

import com.google.common.collect.Lists;
import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.atta.AttaInfo;
import com.wj.core.entity.commodity.Commodity;
import com.wj.core.entity.commodity.Lables;
import com.wj.core.repository.activity.ActivityRepository;
import com.wj.core.repository.commodity.CommodityRepository;
import com.wj.core.repository.commodity.LablesRepository;
import com.wj.core.repository.order.OrderInfoRepository;
import com.wj.core.service.atta.AttaService;
import com.wj.core.util.number.RandomUtil;
import com.wj.core.util.time.ClockUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import sun.misc.ConditionLock;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommodityService {

    @Autowired
    private CommodityRepository commodityRepository;
    @Autowired
    private LablesRepository lablesRepository;
    @Autowired
    private AttaService attaService;
    @Autowired
    private OrderInfoRepository orderInfoRepository;
    @Value("${wj.oss.access}")
    private String url;

    public Commodity findById(Integer commodityId) {
        return commodityRepository.findByCommodityId(commodityId);
    }


    public void saveLables(Lables lables) {
        if (lables.getId() == null) {
            lables.setCreateDate(ClockUtil.currentDate());
        }
        lablesRepository.save(lables);
    }

    public Page<Lables> getLables(Integer pageSize, Integer pageNo) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pageable page = PageRequest.of(pageNo - 1, pageSize, Sort.Direction.DESC, "id");
        return lablesRepository.findAll(page);
    }

    public void removeLables(Integer id) {
        lablesRepository.deleteById(id);
    }

    @Transactional
    public void saveCommodity(Commodity commodity, Integer userId) {
        if (commodity.getId() != null) {
            attaService.removeAtta(commodity.getId(), "COMM");
            commodity.setUpdateDate(ClockUtil.currentDate());
            Integer count = orderInfoRepository.findByCommodityId(commodity.getId());
            commodity.setSalesNum(count);
        } else {
            commodity.setCreateDate(ClockUtil.currentDate());
            commodity.setStatus("0");
            commodity.setCode("C" + ClockUtil.currentTimeMillis());
        }
        commodity.setUserId(userId);
        Commodity comm = commodityRepository.save(commodity);
        if (StringUtils.isNotBlank(commodity.getUploadImg())) {
            String[] imgs = commodity.getUploadImg().split(",");
            for (String img : imgs) {
                AttaInfo attaInfo = new AttaInfo();
                attaInfo.setCreateDate(ClockUtil.currentDate());
                if (StringUtils.contains(commodity.getUploadImg(), "https://")) {
                    attaInfo.setAttaAddr(img);
                } else {
                    attaInfo.setAttaAddr(url + img);
                }
                attaInfo.setObjectId(comm.getId());
                attaInfo.setObjectType("COMM");
                attaInfo.setType("1");
                attaService.saveAttaInfo(attaInfo);
            }
        }
    }

    public Page<Commodity> getCommodityList(String code, Integer pageNum, Integer pageSize) {
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = Lists.newArrayList();
            if (StringUtils.isNotBlank(code)) {
                predicates.add(criteriaBuilder.like(root.get("code"), code + "%"));
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
        Page<Commodity> commodityPage = commodityRepository.findAll(specification, page);
        commodityPage.forEach(commodity -> {
            List<AttaInfo> attaInfos = attaService.getAttaList(commodity.getId(), "COMM");
            commodity.setAttaInfos(attaInfos);
        });
        return commodityPage;
    }

    public void removeCommodity(Integer id) {
        commodityRepository.deleteById(id);
    }



}
