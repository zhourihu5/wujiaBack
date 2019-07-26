package com.wj.core.repository.address;

import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query(value = "select * from ebiz_address where user_id = ?1", nativeQuery = true)
    public List<Address> findByUserId(Integer userId);

    @Query(value = "select * from ebiz_address where userId = ?1 and status = ?2", nativeQuery = true)
    public Address findByAddressId(Integer userId, String status);

    @Query(value = "update ebiz_address set status = ?2 where userId = ?1", nativeQuery = true)
    public void updateAllStatusByUserId(Integer userId, String status);

    @Query(value = "update ebiz_address set status = ?2 where id = ?1", nativeQuery = true)
    public void updateById(Integer addressId, String status);
}
