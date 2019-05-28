package com.api.mapper;

import com.api.entity.BaseFloor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BaseFloorMapper {

    public BaseFloor findByName(String name);

    public List<BaseFloor> allList();

    public List<BaseFloor> findById(String id);




}
