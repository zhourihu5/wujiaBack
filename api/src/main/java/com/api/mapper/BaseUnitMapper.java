package com.api.mapper;

import com.api.entity.BaseUnit;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BaseUnitMapper {

    public BaseUnit findByName(String name);

    public List<BaseUnit> allList();

    public List<BaseUnit> findById(String id);



}
