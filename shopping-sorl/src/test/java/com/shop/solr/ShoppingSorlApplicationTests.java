package com.shop.solr;

import com.shop.mapper.ItemMapper;
import com.shop.pojo.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingSorlApplicationTests {

    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private ItemMapper itemMapper;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testAdd(){
        Item item = new Item();
        item.setId(18888L);
        item.setTitle("小米电冰箱xx");
        item.setPrice(5000D);
        item.setImage("1233.jpg");

        solrTemplate.saveBean("collection1", item);
        solrTemplate.commit("collection1");

        System.out.println("数据导入完成！");
    }

    @Test
    public void textImportData(){
        List<Item> list = itemMapper.selectByExample(null);

        solrTemplate.saveBeans("collection1", list);
        solrTemplate.commit("collection1");
    }

    @Test
    public void testQuery(){
        Query query = new SimpleQuery();
        Criteria criteria = new Criteria("item_title").endsWith("手机");
        query.addCriteria(criteria);

        query.setRows(20);
        ScoredPage<Item> page = solrTemplate.query("collection1", query, Item.class);

        List<Item> list = page.getContent();
        for (Item item: list) {
            System.out.println(item.getTitle()+" , " + item.getImage());
        }
    }

}

