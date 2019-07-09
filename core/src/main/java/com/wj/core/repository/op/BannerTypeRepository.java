package com.wj.core.repository.op;

import com.wj.core.entity.op.OpBanner;
import com.wj.core.entity.op.OpBannerType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BannerTypeRepository extends JpaRepository<OpBannerType, Integer> {


}
