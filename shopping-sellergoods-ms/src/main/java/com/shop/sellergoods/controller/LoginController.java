package com.shop.sellergoods.controller;

import com.shop.pojo.Seller;
import com.shop.pojo.User;
import com.shop.sellergoods.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    public LoginService loginService;


    @RequestMapping("/businessLogin")
    public Seller businessLogin(String sellerId, String passWord){
        Seller seller = loginService.businessLogin(sellerId, passWord);
        return seller;
    }

    @RequestMapping("/operatorLogin")
    public User operatorLogin(String userName, String passWord){
        return loginService.operatorLogin(userName,passWord);
    }
}
