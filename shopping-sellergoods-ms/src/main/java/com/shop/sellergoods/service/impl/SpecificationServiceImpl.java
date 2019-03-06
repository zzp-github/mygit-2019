package com.shop.sellergoods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shop.entity.PageResult;
import com.shop.mapper.SpecificationMapper;
import com.shop.mapper.SpecificationOptionMapper;
import com.shop.pojo.Specification;
import com.shop.pojo.SpecificationExample;
import com.shop.pojo.SpecificationOption;
import com.shop.pojo.SpecificationOptionExample;
import com.shop.pojogroup.Specifications;
import com.shop.sellergoods.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;

    @Override
    public List<Specification> findAll() {
        return specificationMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        //1.启动分页插件
        PageHelper.startPage(pageNum, pageSize);
        Page<Specification> page = (Page<Specification>) specificationMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper ;
    /**
     * 增加
     */
    @Override
    public void add(Specifications specifications) {//Specifications是一个自定义的组合类
        //先将规格名ID先保存到specification表上。
        specificationMapper.insert(specifications.getSpecification());
        //遍历组合类中的规格选项，保存到数据库的specificationOption表中
        for (SpecificationOption specificationOption : specifications.getSpecificationOptionList()){
            //设置规格详细的规格ID
            specificationOption.setSpecId(specificationMapper.selecLastId().getId());

            //保存规格明细到数据库
            specificationOptionMapper.insert(specificationOption);
        }
    }


    /**
     * 修改
     */
    @Override
    public void update(Specifications specifications){
        // 修改规格
        specificationMapper.updateByPrimaryKey(specifications.getSpecification());

        // 先删除规格选项，再添加规格选项
        SpecificationOptionExample example = new SpecificationOptionExample();
        SpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(specifications.getSpecification().getId());
        specificationOptionMapper.deleteByExample(example);

        // 保存规格选项
        for(SpecificationOption specificationOption: specifications.getSpecificationOptionList()){
            // 设置规格的ID:
            specificationOption.setSpecId(specifications.getSpecification().getId());

            specificationOptionMapper.insert(specificationOption);
        }
    }

    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    @Override
    public Specifications findOne(Long id){
        Specifications specifications = new Specifications();
        // 根据规格ID查询规格对象
        Specification tbSpecification = specificationMapper.selectByPrimaryKey(id);
        specifications.setSpecification(tbSpecification);

        // 根据规格的ID查询规格选项
        SpecificationOptionExample example = new SpecificationOptionExample();
        SpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(id);
        List<SpecificationOption> list = specificationOptionMapper.selectByExample(example);
        specifications.setSpecificationOptionList(list);

        return specifications;
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for(Long id:ids){
            // 删除规格
            specificationMapper.deleteByPrimaryKey(id);

            // 删除规格选项:
            SpecificationOptionExample example = new SpecificationOptionExample();
            SpecificationOptionExample.Criteria criteria = example.createCriteria();
            criteria.andSpecIdEqualTo(id);
            specificationOptionMapper.deleteByExample(example);
        }
    }


    @Override
    public PageResult findPage(Specification specification, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        SpecificationExample example=new SpecificationExample();
        SpecificationExample.Criteria criteria = example.createCriteria();

        if(specification!=null){
            if(specification.getSpecName()!=null && specification.getSpecName().length()>0){
                criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
            }

        }

        Page<Specification> page= (Page<Specification>)specificationMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<Map> selectOptionList() {
        return specificationMapper.selectOptionList();
    }
}
