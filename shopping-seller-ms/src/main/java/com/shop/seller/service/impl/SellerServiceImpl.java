package com.shop.seller.service.impl;

import com.shop.mapper.SellerMapper;
import com.shop.pojo.Seller;
import com.shop.seller.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    public SellerMapper sellerMapper;

    @Autowired
    public RedisTemplate redisTemplate;

    @Override
    public Seller findByKey(String sellerId) {
            Seller seller = sellerMapper.selectByPrimaryKey(sellerId);
            return seller;
    }
    @Override
    public Seller findCacheSeller(){
        HashMap map = (HashMap)redisTemplate.boundHashOps("seller").get("seller");
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
