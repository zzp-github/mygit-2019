package com.shop.seller.controller;

import com.shop.entity.Result;
import com.shop.pojo.Seller;
import com.shop.seller.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    public SellerService sellerService;

    @RequestMapping("/findByKey")
    public Seller findByKey(String sellerId){

        Seller seller = sellerService.findByKey(sellerId);
        return seller;
    }

    @RequestMapping("/findCacheSeller")
    public Seller findCacheSeller(){
        Seller cacheSeller = sellerService.findCacheSeller();
        System.out.println("@@@@@="+cacheSeller);
            return cacheSeller;
    }

    @RequestMapping("/update")
    public Result update(@RequestBody Seller entity){
        try {
            sellerService.update(entity);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    @RequestMapping("/updatePassword")
    public Result updatePassword(String oldPassWord,String newPassWord1,String sellerId){
        try {
            Seller cacheSeller = findCacheSeller();
            if (oldPassWord.equals(cacheSeller.getPassword())){
                sellerService.updatePassword(newPassWord1,sellerId);
            }
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

}
