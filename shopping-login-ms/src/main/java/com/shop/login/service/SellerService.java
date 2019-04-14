package com.shop.login.service;

import com.shop.pojo.Seller;

public interface SellerService {

    public Seller login(String sellerId, String passWord);
}
