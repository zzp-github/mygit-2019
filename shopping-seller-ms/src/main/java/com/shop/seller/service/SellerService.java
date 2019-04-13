package com.shop.seller.service;

import com.shop.pojo.Seller;

public interface SellerService {

    public Seller findByKey(String sellerId);

    public Seller findCacheSeller();

    public void update(Seller seller);

    public void updatePassword(String passWord,String sellerId);

}
