package com.shop.login.service;

import com.shop.pojo.Seller;
import com.shop.pojo.User;

public interface LoginService {

    public Seller businessLogin(String sellerId, String passWord);

    public User operatorLogin(String userName, String passWord);

}
