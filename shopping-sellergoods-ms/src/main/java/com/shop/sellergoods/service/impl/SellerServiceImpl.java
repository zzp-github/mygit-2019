package com.shop.sellergoods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shop.entity.PageResult;
import com.shop.mapper.SellerMapper;
import com.shop.pojo.Seller;
import com.shop.pojo.SellerExample;
import com.shop.sellergoods.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private SellerMapper sellerMapper;

	@Autowired
	public RedisTemplate redisTemplate;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<Seller> findAll() {
		return sellerMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<Seller> page=   (Page<Seller>) sellerMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	public List<Seller> findStatus(){
		return sellerMapper.selectByStatus();
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Seller seller) {		
		seller.setStatus("0");//状态
		seller.setCreateTime(new Date());//申请日期
		sellerMapper.insert(seller);		
	}

	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Seller findOne(String id){
		return sellerMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(String[] ids) {
		for(String id:ids){
			sellerMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(Seller seller, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		SellerExample example=new SellerExample();
		SellerExample.Criteria criteria = example.createCriteria();
		
		if(seller!=null){			
						if(seller.getSellerId()!=null && seller.getSellerId().length()>0){
				criteria.andSellerIdLike("%"+seller.getSellerId()+"%");
			}
			if(seller.getName()!=null && seller.getName().length()>0){
				criteria.andNameLike("%"+seller.getName()+"%");
			}
			if(seller.getNickName()!=null && seller.getNickName().length()>0){
				criteria.andNickNameLike("%"+seller.getNickName()+"%");
			}
			if(seller.getPassword()!=null && seller.getPassword().length()>0){
				criteria.andPasswordLike("%"+seller.getPassword()+"%");
			}
			if(seller.getEmail()!=null && seller.getEmail().length()>0){
				criteria.andEmailLike("%"+seller.getEmail()+"%");
			}
			if(seller.getMobile()!=null && seller.getMobile().length()>0){
				criteria.andMobileLike("%"+seller.getMobile()+"%");
			}
			if(seller.getTelephone()!=null && seller.getTelephone().length()>0){
				criteria.andTelephoneLike("%"+seller.getTelephone()+"%");
			}
			if(seller.getStatus()!=null && seller.getStatus().length()>0){
				criteria.andStatusLike("%"+seller.getStatus()+"%");
			}
			if(seller.getAddressDetail()!=null && seller.getAddressDetail().length()>0){
				criteria.andAddressDetailLike("%"+seller.getAddressDetail()+"%");
			}
			if(seller.getLinkmanName()!=null && seller.getLinkmanName().length()>0){
				criteria.andLinkmanNameLike("%"+seller.getLinkmanName()+"%");
			}
			if(seller.getLinkmanQq()!=null && seller.getLinkmanQq().length()>0){
				criteria.andLinkmanQqLike("%"+seller.getLinkmanQq()+"%");
			}
			if(seller.getLinkmanMobile()!=null && seller.getLinkmanMobile().length()>0){
				criteria.andLinkmanMobileLike("%"+seller.getLinkmanMobile()+"%");
			}
			if(seller.getLinkmanEmail()!=null && seller.getLinkmanEmail().length()>0){
				criteria.andLinkmanEmailLike("%"+seller.getLinkmanEmail()+"%");
			}
			if(seller.getLicenseNumber()!=null && seller.getLicenseNumber().length()>0){
				criteria.andLicenseNumberLike("%"+seller.getLicenseNumber()+"%");
			}
			if(seller.getTaxNumber()!=null && seller.getTaxNumber().length()>0){
				criteria.andTaxNumberLike("%"+seller.getTaxNumber()+"%");
			}
			if(seller.getOrgNumber()!=null && seller.getOrgNumber().length()>0){
				criteria.andOrgNumberLike("%"+seller.getOrgNumber()+"%");
			}
			if(seller.getLogoPic()!=null && seller.getLogoPic().length()>0){
				criteria.andLogoPicLike("%"+seller.getLogoPic()+"%");
			}
			if(seller.getBrief()!=null && seller.getBrief().length()>0){
				criteria.andBriefLike("%"+seller.getBrief()+"%");
			}
			if(seller.getLegalPerson()!=null && seller.getLegalPerson().length()>0){
				criteria.andLegalPersonLike("%"+seller.getLegalPerson()+"%");
			}
			if(seller.getLegalPersonCardId()!=null && seller.getLegalPersonCardId().length()>0){
				criteria.andLegalPersonCardIdLike("%"+seller.getLegalPersonCardId()+"%");
			}
			if(seller.getBankUser()!=null && seller.getBankUser().length()>0){
				criteria.andBankUserLike("%"+seller.getBankUser()+"%");
			}
			if(seller.getBankName()!=null && seller.getBankName().length()>0){
				criteria.andBankNameLike("%"+seller.getBankName()+"%");
			}
	
		}
		
		Page<Seller> page= (Page<Seller>)sellerMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public void updateStatus(String sellerId, String status) {
		
		Seller seller = sellerMapper.selectByPrimaryKey(sellerId);
		seller.setStatus(status);
		sellerMapper.updateByPrimaryKey(seller);
	}

	@Override
	public Seller findByKey(String sellerId) {
		Seller seller = sellerMapper.selectByPrimaryKey(sellerId);
		return seller;
	}
	@Override
	public Seller findCacheSeller(){
		HashMap map = (HashMap)redisTemplate.boundHashOps("seller").get("seller");
		if (map==null){
			return null;
		}
		Seller seller=null;
		Set set = map.keySet();
		Iterator iter = set.iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			seller = (Seller)map.get(key);
		}
		return seller;
	}

	@Override
	public void update(Seller seller) {
		sellerMapper.updateByPrimaryKey(seller);
		redisTemplate.boundHashOps("seller").delete("seller");
		HashMap<String,Seller> map = new HashMap<>();
		map.put(seller.getSellerId(),seller);
		redisTemplate.boundHashOps("seller").put("seller",map);
	}

	@Override
	public void updatePassword(String passWord, String sellerId) {
		HashMap<Object, Object> map = new HashMap<>();
		map.put("passWord", passWord);
		map.put("sellerId", sellerId);
		sellerMapper.updatePassWord(map);
		redisTemplate.boundHashOps("seller").delete("seller");
		HashMap<String,Seller> map1 = new HashMap<>();
		Seller seller = sellerMapper.selectByPrimaryKey(sellerId);
		map.put(sellerId,seller);
		redisTemplate.boundHashOps("seller").put("seller",map1);
	}
	
}
