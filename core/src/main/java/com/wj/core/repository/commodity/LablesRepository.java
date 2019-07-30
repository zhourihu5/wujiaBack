package com.wj.core.repository.commodity;

import com.wj.core.entity.commodity.Lables;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LablesRepository extends JpaRepository<Lables, Integer> {

    Page<Lables> findByLablesName(String lablesName, Pageable page);

}
