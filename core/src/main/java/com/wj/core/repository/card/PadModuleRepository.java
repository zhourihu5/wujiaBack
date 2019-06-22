package com.wj.core.repository.card;

import com.wj.core.entity.card.PadModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PadModuleRepository extends JpaRepository<PadModule, Integer> {

    public List<PadModule> findByParentId(Integer id);

}
