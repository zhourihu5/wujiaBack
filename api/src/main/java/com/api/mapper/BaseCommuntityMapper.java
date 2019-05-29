package com.api.mapper;

import com.api.entity.BaseCommuntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BaseCommuntityMapper {

    public List<BaseCommuntity> findByArea(String code);

    public List<BaseCommuntity> allList();

    public BaseCommuntity findById(int id);



}
