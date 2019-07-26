package com.wj.core.service.address;

import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.address.Address;
import com.wj.core.repository.activity.ActivityRepository;
import com.wj.core.repository.address.AddressRepository;
import com.wj.core.repository.commodity.CommodityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;


    public List<Address> findListByUserId(Integer userId) {
        List<Address> activityList = addressRepository.findByUserId(userId);
        return activityList;
    }

    public Address findByAddressId(Integer userId, String status) {
        return addressRepository.findByAddressId(userId, status);
    }

}
