package com.wj.core.repository.activity;

import com.wj.core.entity.activity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BlackListRepository extends JpaRepository<BlackList, Integer>, JpaSpecificationExecutor<BlackList> {


}
