package com.shop.sellergoods.service;

import com.shop.entity.PageResult;
import com.shop.pojo.Brand;

import java.util.List;
import java.util.Map;

public interface BrandService {
    public List<Brand> findAll();

    /**
     * 品牌分页
     * @param pageNum 当前页面
     * @param pageSize 每页记录数
     * @return
     */
    public PageResult findPage(int pageNum,int pageSize);

    /**
     * 增加
     * @param brand
     */
    public void add(Brand brand);

    /**
     * 根据ID查询实体
     * @param id
     * @return
     */
    public Brand findOne(Long id);

    /**
     * 修改
     * @param brand
     */
    public void update(Brand brand);


    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids);
//
//
//	/**
//	 * 品牌分页
//	 * @param pageNum 当前页面
//	 * @param pageSize 每页记录数
//	 * @return
//	 */
//	public PageResult findPage(TbBrand brand, int pageNum,int pageSize);

    /**
     * 返回下拉列表数据
     * @return
     */
    public List<Map> selectOptionList();

    public PageResult findPage(Brand brand, int pageNum, int pageSize);
}
