package com.shop.content.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shop.content.service.ContentService;
import com.shop.entity.PageResult;
import com.shop.mapper.ContentMapper;
import com.shop.pojo.Content;
import com.shop.pojo.ContentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Content> findAll() {
        return contentMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<Content> page=   (Page<Content>) contentMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }


    @Override
    public PageResult findPage(Content content, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        ContentExample example=new ContentExample();
        ContentExample.Criteria criteria = example.createCriteria();

        if(content!=null){
            if(content.getTitle()!=null && content.getTitle().length()>0){
                criteria.andTitleLike("%"+content.getTitle()+"%");
            }
            if(content.getUrl()!=null && content.getUrl().length()>0){
                criteria.andUrlLike("%"+content.getUrl()+"%");
            }
            if(content.getPic()!=null && content.getPic().length()>0){
                criteria.andPicLike("%"+content.getPic()+"%");
            }
            if(content.getStatus()!=null && content.getStatus().length()>0){
                criteria.andStatusLike("%"+content.getStatus()+"%");
            }

        }

        Page<Content> page= (Page<Content>)contentMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(Content content) {

        contentMapper.insert(content);
        // 清除缓存
        redisTemplate.boundHashOps("content").delete(content.getCategoryId());
    }


    /**
     * 修改
     */
    @Override
    public void update(Content content){
        Content oldContent = contentMapper.selectByPrimaryKey(content.getId());
        // 清除之前分类的广告缓存
        redisTemplate.boundHashOps("content").delete(oldContent.getCategoryId());

        contentMapper.updateByPrimaryKey(content);
        // 清除缓存
        if(content.getCategoryId() != oldContent.getCategoryId()){
            redisTemplate.boundHashOps("content").delete(content.getCategoryId());
        }

    }

    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    @Override
    public Content findOne(Long id){
        return contentMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for(Long id:ids){
            Content tbContent = contentMapper.selectByPrimaryKey(id);
            redisTemplate.boundHashOps("content").delete(tbContent.getCategoryId());

            contentMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public List<Content> findByCategoryId(Long categoryId) {
        // 加入缓存的代码:
        //boundHashOps 该方法用于查询redis中Hash结构的数据
        List<Content> list = (List<Content>) redisTemplate.boundHashOps("content").get(categoryId);

        if(list==null){
            System.out.println("查询数据库===================");
            ContentExample example = new ContentExample();
            ContentExample.Criteria criteria = example.createCriteria();
            // 有效广告:判断是否是有效的广告， status列是判断的依据
            criteria.andStatusEqualTo("1");

            criteria.andCategoryIdEqualTo(categoryId);
            // 排序
            example.setOrderByClause("sort_order");

            list = contentMapper.selectByExample(example);

            redisTemplate.boundHashOps("content").put(categoryId, list);
        }else{
            System.out.println("从缓存中获取====================");
        }
        return list;
    }
}
