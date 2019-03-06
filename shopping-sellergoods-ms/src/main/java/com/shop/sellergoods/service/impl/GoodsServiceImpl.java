package com.shop.sellergoods.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shop.entity.PageResult;
import com.shop.mapper.*;
import com.shop.pojo.*;
import com.shop.pojogroup.GoodsGroup;
import com.shop.sellergoods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 查询全部
     */
    @Override
    public List<Goods> findAll() {
        return goodsMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<Goods> page = (Page<Goods>) goodsMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Autowired
    private GoodsDescMapper goodsDescMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SellerMapper sellerMapper;

    /**
     * 增加
     */
    @Override
    public void add(GoodsGroup goodsGroup) {
        goodsGroup.getGoods().setAuditStatus("0"); // 设置审核的状态

        goodsMapper.insert(goodsGroup.getGoods()); // 插入商品信息

        goodsGroup.getGoodsDesc().setGoodsId(goodsGroup.getGoods().getId());

        goodsDescMapper.insert(goodsGroup.getGoodsDesc()); // 插入商品的扩展信息

        setItemList(goodsGroup);
    }

    /*
是否启用规格：0
com.online.shopping.pojo.TbItem@fb6e9c0
com.online.shopping.pojogroup.Goods@20419a57
com.online.shopping.pojo.TbGoods@467d3c8e
com.online.shopping.pojo.TbGoodsDesc@2427e0ed
[{"color":"黑色","url":"http://localhost:8879/upload/61Z58PICgN6.jpg","$$hashKey":"008"}]
[{color=黑色, $$hashKey=008, url=http://localhost:8879/upload/61Z58PICgN6.jpg}]
560
TbItemCat:  com.online.shopping.pojo.TbItemCat@30d857b9
     * */
    private void setItemList(GoodsGroup goodsGroup) {
        System.out.println("是否启用规格：" + goodsGroup.getGoods().getIsEnableSpec());
        if ("1".equals(goodsGroup.getGoods().getIsEnableSpec())) {
            // 启用规格
            // 保存SKU列表的信息:
            for (Item item : goodsGroup.getItemList()) {
                // 设置SKU的数据：
                // item.setTitle(title);
                String title = goodsGroup.getGoods().getGoodsName();
                Map<String, String> map = JSON.parseObject(item.getSpec(), Map.class);
                for (String key : map.keySet()) {
                    title += " " + map.get(key);
                }
                item.setTitle(title);

                setValue(goodsGroup, item);

                itemMapper.insert(item);
            }
        } else {
            // 没有启用规格
            Item item = new Item();

            item.setTitle(goodsGroup.getGoods().getGoodsName());

            item.setPrice(goodsGroup.getGoods().getPrice());

            item.setNum(999);

            item.setStatus("0");

            item.setIsDefault("1");
            item.setSpec("{}");

            setValue(goodsGroup, item);
            itemMapper.insert(item);
        }
    }

    private void setValue(GoodsGroup goodsGroup, Item item) {
        List<Map> imageList = JSON.parseArray(goodsGroup.getGoodsDesc().getItemImages(), Map.class);

        if (imageList.size() > 0) {
            item.setImage((String) imageList.get(0).get("url"));
        }

        // 保存三级分类的ID:
        item.setCategoryid(goodsGroup.getGoods().getCategory3Id());
        item.setCreateTime(new Date());
        item.setUpdateTime(new Date());
        // 设置商品ID
        item.setGoodsId(goodsGroup.getGoods().getId());
        item.setSellerId(goodsGroup.getGoods().getSellerId());
        ItemCat itemCat = itemCatMapper.selectByPrimaryKey(goodsGroup.getGoods().getCategory3Id());

        item.setCategory(itemCat.getName());

        Brand brand = brandMapper.selectByPrimaryKey(goodsGroup.getGoods().getBrandId());

        item.setBrand(brand.getName());

        Seller seller = sellerMapper.selectByPrimaryKey(goodsGroup.getGoods().getSellerId());

        item.setSeller(seller.getNickName());
    }

    /**
     * 修改
     */
    @Override
    public void update(GoodsGroup goodsGroup) {
        // 修改商品信息
        goodsGroup.getGoods().setAuditStatus("0");
        goodsMapper.updateByPrimaryKey(goodsGroup.getGoods());
        // 修改商品扩展信息:
        goodsDescMapper.updateByPrimaryKey(goodsGroup.getGoodsDesc());
        // 修改SKU信息:
        // 先删除，再保存:
        // 删除SKU的信息:
        ItemExample example = new ItemExample();
        ItemExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(goodsGroup.getGoods().getId());
        itemMapper.deleteByExample(example);
        // 保存SKU的信息
        setItemList(goodsGroup);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public GoodsGroup findOne(Long id) {
        GoodsGroup goods = new GoodsGroup();
        // 查询商品表的信息
        Goods tbGoods = goodsMapper.selectByPrimaryKey(id);
        goods.setGoods(tbGoods);
        // 查询商品扩展表的信息
        GoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(id);
        goods.setGoodsDesc(tbGoodsDesc);

        // 查询SKU表的信息:
        ItemExample example = new ItemExample();
        ItemExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(id);
        List<Item> list = itemMapper.selectByExample(example);
        goods.setItemList(list);

        return goods;
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
//			goodsMapper.deleteByPrimaryKey(id);
            Goods tbGoods = goodsMapper.selectByPrimaryKey(id);
            tbGoods.setIsDelete("1");
            goodsMapper.updateByPrimaryKey(tbGoods);
        }
    }


    @Override
    public PageResult findPage(Goods goods, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        GoodsExample example = new GoodsExample();
        GoodsExample.Criteria criteria = example.createCriteria();

        //criteria.andIsDeleteIsNull();

        if (goods != null) {
            if (goods.getSellerId() != null && goods.getSellerId().length() > 0) {
                System.out.println("设置sellerId: " + goods.getSellerId());
                criteria.andSellerIdEqualTo(goods.getSellerId());
            }
            if (goods.getGoodsName() != null && goods.getGoodsName().length() > 0) {
                criteria.andGoodsNameLike("%" + goods.getGoodsName() + "%");
            }
            if (goods.getAuditStatus() != null && goods.getAuditStatus().length() > 0) {
                criteria.andAuditStatusLike("%" + goods.getAuditStatus() + "%");
            }
            if (goods.getIsMarketable() != null && goods.getIsMarketable().length() > 0) {
                criteria.andIsMarketableLike("%" + goods.getIsMarketable() + "%");
            }
            if (goods.getCaption() != null && goods.getCaption().length() > 0) {
                criteria.andCaptionLike("%" + goods.getCaption() + "%");
            }
            if (goods.getSmallPic() != null && goods.getSmallPic().length() > 0) {
                criteria.andSmallPicLike("%" + goods.getSmallPic() + "%");
            }
            if (goods.getIsEnableSpec() != null && goods.getIsEnableSpec().length() > 0) {
                criteria.andIsEnableSpecLike("%" + goods.getIsEnableSpec() + "%");
            }
            if (goods.getIsDelete() != null && goods.getIsDelete().length() > 0) {
                criteria.andIsDeleteLike("%" + goods.getIsDelete() + "%");
            }

        }

        Page<Goods> page = (Page<Goods>) goodsMapper.selectByExample(example);
        System.out.println("##### " + page.getResult());
        System.out.println(page.getTotal());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void updateStatus(Long[] ids, String status) {
        for (Long id : ids) {
            Goods tbGoods = goodsMapper.selectByPrimaryKey(id);

            tbGoods.setAuditStatus(status);

            goodsMapper.updateByPrimaryKey(tbGoods);
        }
    }

    @Override
    public List<Item> findItemListByGoodsIdListAndStatus(Long[] goodsIds, String status) {
        System.out.println("Goods Id列表：" + goodsIds);
        System.out.println("状态：" + status);
        ItemExample example = new ItemExample();
        ItemExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(status);//状态
        criteria.andGoodsIdIn(Arrays.asList(goodsIds));//指定条件：SPUID集合

        return itemMapper.selectByExample(example);
    }

}
