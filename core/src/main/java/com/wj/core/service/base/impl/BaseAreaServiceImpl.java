package com.wj.core.service.base.impl;

import com.google.common.collect.Lists;
import com.wj.core.entity.base.BaseArea;
import com.wj.core.entity.base.dto.AreaDTO;
import com.wj.core.entity.base.dto.CityDTO;
import com.wj.core.entity.base.dto.ProvinceDTO;
import com.wj.core.repository.base.BaseAreaRepository;
import com.wj.core.service.base.BaseAreaService;
import com.wj.core.util.mapper.BeanMapper;
import com.wj.core.util.mapper.JsonMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseAreaServiceImpl implements BaseAreaService {

    @Autowired
    private BaseAreaRepository areaRepository;



    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    static JsonMapper mapper = JsonMapper.defaultMapper();

    /**
     * 根据id查询省市区信息
     *
     * @param id
     * @return BaseArea
     */
    @Cacheable("areaList")
    public BaseArea findById(Integer id) {
        return areaRepository.findByCityId(id);
    }

    /**
     * 根据id查询省市区信息
     *
     * @param id
     * @return List<BaseArea>
     */
    @Cacheable(value = "area", key = "targetClass + ':' + methodName + '_' + #p0", unless = "#result.size() <= 0")
    public List<BaseArea> findByPid(Integer id) {
        return areaRepository.findByPid(id);
    }

    @Override
    public List<ProvinceDTO> getALl() {
        List<ProvinceDTO> provinceDTOS = Lists.newArrayList();
        if (stringRedisTemplate.hasKey("areaList")) {
            String area = stringRedisTemplate.opsForValue().get("areaList");
            return mapper.fromJson(area, mapper.buildCollectionType(List.class, ProvinceDTO.class));
        }
        List<BaseArea> pList = findByPid(0);
        for (BaseArea s : pList) {
            ProvinceDTO provinceDTO = BeanMapper.map(s, ProvinceDTO.class);
            List<BaseArea> cList = findByPid(s.getId());
            List<CityDTO> cityDTOS = Lists.newArrayList();
            for (BaseArea c : cList) {
                CityDTO cityDTO = BeanMapper.map(c, CityDTO.class);
                List<BaseArea> aList = findByPid(c.getId());
                List<AreaDTO> areaDTOS = Lists.newArrayList();
                for (BaseArea a : aList) {
                    AreaDTO areaDTO = BeanMapper.map(a, AreaDTO.class);
                    areaDTOS.add(areaDTO);
                }
                cityDTO.setChildren(areaDTOS);
                cityDTOS.add(cityDTO);
            }
            provinceDTO.setChildren(cityDTOS);
            provinceDTOS.add(provinceDTO);
        }
        stringRedisTemplate.opsForValue().set("areaList", mapper.toJson(provinceDTOS));
        return provinceDTOS;
    }

}
