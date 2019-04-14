package com.shop.seller.controller;

import com.shop.pojo.Seller;
import com.shop.seller.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    public SellerService sellerService;

    @RequestMapping("/findAll")
    public Seller findAll(String sellerId){
        Seller seller = sellerService.findAll(sellerId);

        return seller;
    }

}
