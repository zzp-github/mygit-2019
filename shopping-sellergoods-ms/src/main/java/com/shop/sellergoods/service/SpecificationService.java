package com.shop.sellergoods.service;

import com.shop.entity.PageResult;
import com.shop.pojo.Specification;
import com.shop.pojogroup.Specifications;

import java.util.List;
import java.util.Map;

public interface SpecificationService {

    public List<Specification> findAll();

    /**
     * 返回分页列表
     * @return
     */
    public PageResult findPage(int pageNum,int pageSize);


    /**
     * 增加
     */
    public void add(Specifications specifications);


    /**
     * 修改
     */
    public void update(Specifications specifications);


    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    public Specifications findOne(Long id);


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
    public PageResult findPage(Specification specification, int pageNum, int pageSize);

    public List<Map> selectOptionList();
}
