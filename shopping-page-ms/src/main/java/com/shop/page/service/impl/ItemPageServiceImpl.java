package com.shop.page.service.impl;

import com.shop.mapper.GoodsDescMapper;
import com.shop.mapper.GoodsMapper;
import com.shop.mapper.ItemCatMapper;
import com.shop.mapper.ItemMapper;
import com.shop.page.service.ItemPageService;
import com.shop.pojo.Goods;
import com.shop.pojo.GoodsDesc;
import com.shop.pojo.Item;
import com.shop.pojo.ItemExample;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemPageServiceImpl implements ItemPageService {

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	//@Value("${pagedir}")
	private String pagedir = "E:\\ProgramData\\Java\\tempData\\item\\";
	
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private GoodsDescMapper goodsDescMapper;
	
	@Autowired
	private ItemCatMapper itemCatMapper;
	
	@Autowired
	private ItemMapper itemMapper;



	@Override
	public boolean genItemHtml(Long goodsId) {
		//使用freemarker静态模板生成静态网页
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		
		try {
			Template template = configuration.getTemplate("item.ftl");
			//创建数据模型
			Map dataModel=new HashMap<>();
			//1.商品主表数据
			Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
			dataModel.put("goods", goods);
			//2.商品扩展表数据
			GoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
			dataModel.put("goodsDesc", goodsDesc);
			//3.读取商品分类
			
			String itemCat1 = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
			String itemCat2 = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
			String itemCat3 = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
			dataModel.put("itemCat1", itemCat1);
			dataModel.put("itemCat2", itemCat2);
			dataModel.put("itemCat3", itemCat3);
			
			//4.读取SKU列表
			ItemExample example=new ItemExample();
			ItemExample.Criteria criteria = example.createCriteria();
			criteria.andGoodsIdEqualTo(goodsId);//SPU ID
			criteria.andStatusEqualTo("1");//状态有效			
			example.setOrderByClause("is_default desc");//按是否默认字段进行降序排序，目的是返回的结果第一条为默认SKU
			
			List<Item> itemList = itemMapper.selectByExample(example);
			dataModel.put("itemList", itemList);
			
			Writer out=new FileWriter(pagedir+goodsId+".html");
			
			template.process(dataModel, out);//输出
			out.close();
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
		
	}

	@Override
	public boolean deleteItemHtml(Long[] goodsIds) {
		try {
			for(Long goodsId:goodsIds){
				new File(pagedir+goodsId+".html").delete();		
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
	}

}
