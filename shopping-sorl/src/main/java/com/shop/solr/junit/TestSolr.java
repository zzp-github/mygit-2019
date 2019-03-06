package com.shop.solr.junit;

import com.shop.mapper.ItemMapper;
import com.shop.pojo.Item;
import com.shop.pojo.ItemExample;
import com.shop.solr.SorlApp;
import com.shop.solr.pojo.Items;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {SorlApp.class})
public class TestSolr {
	
	@Autowired
	private SolrTemplate solrTemplate;
	
	@Autowired
	private ItemMapper itemMapper;

	@Test
	public void testAdd() {
		Items item = new Items();
		item.setId(9099);
		item.setName("松下洗衣机II");
		item.setPrice(6);
		item.setDetail("very good");
		
		solrTemplate.saveBean("collection1", item);
		solrTemplate.commit("collection1");
		
		System.out.println("添加完毕");
	}
	
	@Test
	public void testDelete() {
		List<String> list = new ArrayList<>();
		list.add("item001");
		list.add("item002");
		list.add("item888");
		list.add("item004");
		list.add("item003");
		solrTemplate.deleteByIds("collection1", list);
		solrTemplate.commit("collection1");
	}
	
	@Test
	public void testFindOne() {
		Optional<Items> item = solrTemplate.getById("collection1", 9099, Items.class);
	    Items items = item.get();
	    System.out.println(items.getName() + "," + items.getPrice());
	}
	
	@Test
	public void importData() {
		ItemExample example = new ItemExample();
		
		List<Item> list = itemMapper.selectByExample(example);
		for (Item item : list) {
			System.out.println(item.getId()+"|"+item.getTitle()+"|"+item.getPrice());
		}
		
		System.out.println("数据正在导入solr.......");
		
		solrTemplate.saveBeans("collection1", list);
		solrTemplate.commit("collection1");
		
		System.out.println("结束!");
	}
	
}
