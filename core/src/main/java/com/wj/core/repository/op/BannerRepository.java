package com.wj.core.repository.op;

import com.wj.core.entity.op.OpBanner;
import com.wj.core.entity.op.OpService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BannerRepository extends JpaRepository<OpBanner, Integer> {


    /**
     * 轮播图列表
     * @param
     * @return int
     */
    @Query(value = "select * from op_banner where module_type = ?1", nativeQuery = true)
    public List<OpBanner> findByModuleTypeList(Integer type);

    @Query(value = "select * from op_banner where module_type = ?1", nativeQuery = true)
    public Page<OpBanner> findByType(Integer type, Pageable pageable);
}
