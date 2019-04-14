package com.shop.login.service.impl;

import com.shop.login.service.SellerService;
import com.shop.mapper.SellerMapper;
import com.shop.pojo.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    public SellerMapper sellerMapper;

    @Override
    public Seller login(String sellerId, String passWord) {
        Map map = new HashMap();
        map.put("sellerId", sellerId);
        map.put("passWord", passWord);
        return sellerMapper.login(map);
    }
}
