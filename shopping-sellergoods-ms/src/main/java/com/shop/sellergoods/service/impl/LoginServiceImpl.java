package com.shop.sellergoods.service.impl;

import com.shop.mapper.SellerMapper;
import com.shop.mapper.UserMapper;
import com.shop.pojo.Seller;
import com.shop.pojo.User;
import com.shop.sellergoods.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    public SellerMapper sellerMapper;

    @Autowired
    public UserMapper userMapper;

    @Autowired
    public RedisTemplate redisTemplate;

    @Override
    public Seller businessLogin(String sellerId, String passWord) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("sellerId", sellerId);
        map.put("passWord", passWord);
        Seller seller = sellerMapper.selectBySelleridPassword(map);
        if (seller != null){
            System.out.println("^^^^^^=" + seller.getName());
            HashMap<String,Seller> map1 = new HashMap<>();
            map1.put(sellerId,seller);
            redisTemplate.boundHashOps("seller").put("seller",map1);
            return seller;
        }else{
            return null;
        }
    }

    @Override
    public User operatorLogin(String userName, String passWord) {
        HashMap<Object, Object> m = new HashMap<>();
        m.put("userName", userName);
        m.put("passWord", passWord);
        User user = userMapper.selectByUserNamePassWord(m);
        if (user!=null){
            if(user.getAdmin()!=0){
                HashMap<String, User> map1 = new HashMap<>();
                map1.put(userName,user);
                redisTemplate.boundHashOps("adminUser").put("adminUser",map1);
                return user;
            }
        }
        return null;
    }
}
