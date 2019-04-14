package com.shop.login.controller;

import com.shop.login.service.SellerService;
import com.shop.login.service.UserService;
import com.shop.pojo.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    public UserService userService;

    @Autowired
    public SellerService sellerService;


    @RequestMapping("/BusinessLogin")
    @ResponseBody
    public Seller login(@RequestParam(value = "sellerId") String sellerId,
                        @RequestParam(value = "passWord") String passWord){

        return sellerService.login(sellerId, passWord);
    }

}
