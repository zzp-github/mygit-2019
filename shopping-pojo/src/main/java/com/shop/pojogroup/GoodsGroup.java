package com.shop.pojogroup;

import com.shop.pojo.Goods;
import com.shop.pojo.GoodsDesc;
import com.shop.pojo.Item;

import java.io.Serializable;
import java.util.List;


/**
 * 商品的组合实体类
 * @author jt
 *
 */

public class GoodsGroup implements Serializable{
	
	private Goods goods; // 商品信息
	private GoodsDesc goodsDesc; // 商品扩展信息
	
	private List<Item> itemList; // SKU的列表信息
	
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public GoodsDesc getGoodsDesc() {
		return goodsDesc;
	}
	public void setGoodsDesc(GoodsDesc goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
	public List<Item> getItemList() {
		return itemList;
	}
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	
}
