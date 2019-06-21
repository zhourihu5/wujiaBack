package com.wj.core.repository.op;

import com.wj.core.entity.op.OpAdv;
import com.wj.core.entity.op.OpAdvUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface AdvRepository extends JpaRepository<OpAdv, Integer> {



}
