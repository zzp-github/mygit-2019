package com.shop.login.service;

import com.shop.pojo.Seller;
import com.shop.pojo.User;

public interface UserService {

    public User findCacheUser();

    public Seller findByKey(String sellerId);

    public Seller findCacheSeller();

    public void update(Seller seller);

    public void updatePassword(String passWord, String sellerId);
}
