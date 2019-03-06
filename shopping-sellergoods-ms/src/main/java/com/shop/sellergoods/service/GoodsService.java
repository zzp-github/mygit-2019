package com.shop.sellergoods.service;

import com.shop.entity.PageResult;
import com.shop.pojo.Goods;
import com.shop.pojo.Item;
import com.shop.pojogroup.GoodsGroup;

import java.util.List;


/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface GoodsService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<Goods> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(GoodsGroup goodsGroup);
	
	
	/**
	 * 修改
	 */
	public void update(GoodsGroup goodsGroup);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public GoodsGroup findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(Goods goods, int pageNum, int pageSize);
	
	public void updateStatus(Long[] ids, String status);


	public List<Item> findItemListByGoodsIdListAndStatus(Long[] ids, String status);
	
}
