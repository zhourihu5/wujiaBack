package com.api.mapper;

import com.api.entity.BaseFamily;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BaseFamilyMapper {

    public BaseFamily findByName(String name);

    public List<BaseFamily> allList();

    public List<BaseFamily> findById(String id);





}
