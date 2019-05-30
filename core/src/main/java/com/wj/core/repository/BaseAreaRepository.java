package com.wj.core.repository;

import com.wj.core.entity.BaseArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseAreaRepository extends JpaRepository<BaseArea, Integer> {

    public List<BaseArea> findAreaByName(String name);

    public List<BaseArea> areaList();

    public List<BaseArea> findAreaByPid(int pid);

    public BaseArea findAreaById(int id);


}
