package com.shop.login.controller;

import com.shop.entity.Result;
import com.shop.login.service.LoginService;
import com.shop.login.service.UserService;
import com.shop.pojo.Seller;
import com.shop.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    public LoginService loginService;

    @Autowired
    private UserService userService;


    @RequestMapping("/businessLogin")
    public Seller businessLogin(String sellerId, String passWord){
        Seller seller = loginService.businessLogin(sellerId, passWord);
        return seller;
    }

    @RequestMapping("/findByKey")
    public Seller findByKey(String sellerId){

        Seller seller = userService.findByKey(sellerId);
        return seller;
    }

    @RequestMapping("/findCacheSeller")
    public Seller findCacheSeller(){
        Seller cacheSeller = userService.findCacheSeller();
        System.out.println("@@@@@="+cacheSeller);
        if (cacheSeller == null){
            return null;
        }
        return cacheSeller;
    }

    @RequestMapping("/updatePassword")
    public Result updatePassword(String oldPassWord, String newPassWord1, String sellerId){
        try {
            Seller cacheSeller = findCacheSeller();
            if (oldPassWord.equals(cacheSeller.getPassword())){
                userService.updatePassword(newPassWord1,sellerId);
            }
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    @RequestMapping("/operatorLogin")
    public User operatorLogin(String userName, String passWord){
        return loginService.operatorLogin(userName,passWord);
    }

    @RequestMapping("/findCacheAdmin")
    public User findCacheAdmin(){
        User cacheUser = userService.findCacheUser();
        if (cacheUser == null){
            return null;
        }
        return cacheUser;
    }
}
