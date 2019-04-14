package com.shop.login.service.impl;

import com.shop.login.service.UserService;
import com.shop.mapper.SellerMapper;
import com.shop.pojo.Seller;
import com.shop.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SellerMapper sellerMapper;

    @Override
    public User findCacheUser(){
        HashMap map = (HashMap)redisTemplate.boundHashOps("adminUser").get("adminUser");
        if (map==null){
            return null;
        }
        User user=null;
        Set set = map.keySet();
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            user = (User)map.get(key);
        }
        return user;
    }

    @Override
    public Seller findByKey(String sellerId) {
        Seller seller = sellerMapper.selectByPrimaryKey(sellerId);
        return seller;
    }
    @Override
    public Seller findCacheSeller(){
        HashMap map = (HashMap)redisTemplate.boundHashOps("seller").get("seller");
        if (map==null){
            return null;
        }
        Seller seller=null;
        Set set = map.keySet();
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            seller = (Seller)map.get(key);
        }
        return seller;
    }

    @Override
    public void update(Seller seller) {
        sellerMapper.updateByPrimaryKey(seller);
        redisTemplate.boundHashOps("seller").delete("seller");
        HashMap<String,Seller> map = new HashMap<>();
        map.put(seller.getSellerId(),seller);
        redisTemplate.boundHashOps("seller").put("seller",map);
    }

    @Override
    public void updatePassword(String passWord, String sellerId) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("passWord", passWord);
        map.put("sellerId", sellerId);
        sellerMapper.updatePassWord(map);
        redisTemplate.boundHashOps("seller").delete("seller");
        HashMap<String,Seller> map1 = new HashMap<>();
        Seller seller = sellerMapper.selectByPrimaryKey(sellerId);
        map.put(sellerId,seller);
        redisTemplate.boundHashOps("seller").put("seller",map1);
    }

}
