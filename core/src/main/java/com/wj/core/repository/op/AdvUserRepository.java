package com.wj.core.repository.op;

import com.wj.core.entity.op.OpAdvUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface AdvUserRepository extends JpaRepository<OpAdvUser, Integer> {

    @Modifying
    @Query(value = "insert into op_adv_user(adv_id, user_id, create_date) values(?1,?2,?3)", nativeQuery = true)
    public Integer addAdvUser(Integer advId, Integer userId, Date date);

}
