package com.shop.sellergoods.service.impl;

import com.shop.pojo.User;
import com.shop.sellergoods.service.UserService;
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
}
