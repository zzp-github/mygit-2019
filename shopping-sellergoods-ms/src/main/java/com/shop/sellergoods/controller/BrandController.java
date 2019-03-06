package com.shop.sellergoods.controller;

import com.shop.entity.PageResult;
import com.shop.entity.Result;
import com.shop.pojo.Brand;
import com.shop.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @RequestMapping("/findAll")
    public List<Brand> findAll(){
        return brandService.findAll();
    }

    @RequestMapping("/findPage")
    public PageResult findPage(int pageNum,int pageSize) {
        return brandService.findPage(pageNum, pageSize);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Brand brand) {
        try {
            brandService.add(brand);
            return new Result(true, "成功添加品牌信息");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加品牌信息失败");
        }
    }

    @RequestMapping("/findBrandDetail")
    public Brand findBrandDetail(Long id) {
        return brandService.findOne(id);
    }

    @RequestMapping("/updateBrand")
    public Result updateBrand(@RequestBody Brand brand) {
        try {
            brandService.update(brand);
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
    public Brand findOne(Long id){
        return brandService.findOne(id);
    }

    @RequestMapping("/delete")
    public Result delete(Long [] ids){
        try {
            brandService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList() {
        return brandService.selectOptionList();
    }
    /**
     * 查询+分页
     * @param content
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody Brand brand, int page, int rows  ){
        return brandService.findPage(brand, page, rows);
    }
}
