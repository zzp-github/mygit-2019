package com.shop.sellergoods.entity;

import com.shop.pojo.Goods;

public class ItemCatEntity {
    public Long id;

    public Goods goods;

    public String name;

    public Long typeId;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
    public Goods getGoods(){
        return goods;
    }
}
