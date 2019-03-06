package com.shop.sellergoods.controller;

import com.shop.entity.PageResult;
import com.shop.entity.Result;
import com.shop.pojo.Specification;
import com.shop.pojogroup.Specifications;
import com.shop.sellergoods.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/specification")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    /**
     * 查询所有列表
     * @return
     */
    @RequestMapping("/findAll")
    public List<Specification> findAllSpecification(){
        return specificationService.findAll();
    }

    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult  findPage(int page,int rows){
        return specificationService.findPage(page, rows);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Specifications specifications){//Specifications是一个自定义的组合类
        try {
            specificationService.add(specifications);
            return new Result(true, "增加成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 修改
     * @param specifications
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Specifications specifications){
        try {
            specificationService.update(specifications);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    /**
     * 获取实体
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public Specifications findOne(Long id){
        return specificationService.findOne(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long [] ids){
        try {
            specificationService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }
    /**
     * 查询+分页
     * @param specification
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody Specification specification, int page, int rows){
        return specificationService.findPage(specification, page, rows);
    }

    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        return specificationService.selectOptionList();
    }
}
