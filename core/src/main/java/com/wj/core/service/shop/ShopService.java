package com.wj.core.service.shop;

import com.wj.core.repository.shop.ShopAddressRepository;
import com.wj.core.repository.shop.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ShopAddressRepository shopAddressRepository;

}
