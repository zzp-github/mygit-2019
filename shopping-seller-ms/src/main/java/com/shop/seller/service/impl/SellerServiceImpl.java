package com.shop.seller.service.impl;

import com.shop.mapper.SellerMapper;
import com.shop.pojo.Seller;
import com.shop.seller.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    public SellerMapper sellerMapper;

    @Override
    public Seller findAll(String sellerId) {
        Seller seller = sellerMapper.selectByPrimaryKey(sellerId);
        return seller;
    }
}
