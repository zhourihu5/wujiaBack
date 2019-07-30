package com.wj.core.service.address;

import com.wj.core.entity.activity.Activity;
import com.wj.core.entity.address.Address;
import com.wj.core.entity.base.BaseCommuntity;
import com.wj.core.entity.card.enums.CardStatus;
import com.wj.core.entity.user.SysUserFamily;
import com.wj.core.repository.activity.ActivityRepository;
import com.wj.core.repository.address.AddressRepository;
import com.wj.core.repository.base.BaseCommuntityRepository;
import com.wj.core.repository.base.FamilyCommuntityRepository;
import com.wj.core.repository.commodity.CommodityRepository;
import com.wj.core.repository.user.UserFamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserFamilyRepository userFamilyRepository;

    @Autowired
    private FamilyCommuntityRepository familyCommuntityRepository;

    @Autowired
    private BaseCommuntityRepository baseCommuntityRepository;

    public List<Address> findListByUserId(Integer userId) {
        List<Address> activityList = addressRepository.findByUserId(userId);
        return activityList;
    }

    public Address findByAddressId(Integer userId, String status) {
        return addressRepository.findByAddressId(userId, status);
    }

    // 根据用户id查询所在社区
    public List<BaseCommuntity> findByUserId(Integer userId) {
        List<BaseCommuntity> list = new ArrayList<>();
        // 根据用户id查询家庭id
        List<SysUserFamily> userFamilyList = userFamilyRepository.findByUserId(userId);
        userFamilyList.forEach(SysUserFamily -> {
            // 根据家庭id查询社区id
            Integer communtityId = familyCommuntityRepository.findByFamilyId(SysUserFamily.getUserFamily().getFamilyId());
            BaseCommuntity baseCommuntity = baseCommuntityRepository.findByCommuntityId(communtityId);
            list.add(baseCommuntity);
        });
        return list;
    }

    // 根据用户id查询所在社区名称
    public String findCommuntityNameByUserId(Integer userId) {
        String name = "";
        List<BaseCommuntity> list = new ArrayList<>();
        // 根据用户id查询家庭id
        List<SysUserFamily> userFamilyList = userFamilyRepository.findByUserId(userId);
        userFamilyList.forEach(SysUserFamily -> {
            // 根据家庭id查询社区id
            Integer communtityId = familyCommuntityRepository.findByFamilyId(SysUserFamily.getUserFamily().getFamilyId());
            BaseCommuntity baseCommuntity = baseCommuntityRepository.findByCommuntityId(communtityId);
            list.add(baseCommuntity);
        });
        if (list.size() > 0) {
            name = list.get(0).getName();
        }
        return name;
    }

    @Transactional
    public void save(Address address) {
        if (address.getStatus() == null) {
            address.setStatus("0");
        }
        addressRepository.save(address);
    }
}
