package com.api.mapper;

import com.api.entity.Area;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AreaMapper {

    public List<Area> findAreaByName(String name);

    public List<Area> areaList();

    public List<Area> findAreaByPid(String pid);

    public Area findProByPid(String pid);


}
