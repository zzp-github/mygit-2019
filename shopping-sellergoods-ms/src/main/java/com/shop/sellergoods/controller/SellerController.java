package com.shop.sellergoods.controller;

import com.alibaba.fastjson.JSON;
import com.shop.entity.PageResult;
import com.shop.entity.Result;
import com.shop.pojo.Item;
import com.shop.pojo.Seller;
import com.shop.pojo.User;
import com.shop.sellergoods.activemq.JmsConfig;
import com.shop.sellergoods.service.SellerService;
import com.shop.sellergoods.service.UserService;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.MessageCreator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;
import java.util.List;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/seller")
public class SellerController {
	@Autowired
	private SellerService sellerService;

	@Autowired
	private UserService userService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<Seller> findAll(){
		return sellerService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int page, int rows){
		return sellerService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param seller
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody Seller seller){
		try {
			sellerService.add(seller);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}

	@RequestMapping("/findStatus")
	public List<Seller> findStatus(){
		List<Seller> status = sellerService.findStatus();
		return status;
	}

	/**
	 * 修改
	 * @param seller
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody Seller seller){
		try {
			sellerService.update(seller);
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
	public Seller findOne(String id){
		return sellerService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(String [] ids){
		try {
			sellerService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody Seller seller, int page, int rows  ){
		return sellerService.findPage(seller, page, rows);		
	}

	@RequestMapping("/updateStatus")
	public Result updateStatus(String sellerId,String status){
		try {
			sellerService.updateStatus(sellerId, status);
			return new Result(true, "修改状态成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改状态失败");
		}
	}

	@RequestMapping("/findByKey")
	public Seller findByKey(String sellerId){

		Seller seller = sellerService.findByKey(sellerId);
		return seller;
	}

	@RequestMapping("/findCacheSeller")
	public Seller findCacheSeller(){
		Seller cacheSeller = sellerService.findCacheSeller();
		System.out.println("@@@@@="+cacheSeller);
		if (cacheSeller == null){
			return null;
		}
		return cacheSeller;
	}
	@RequestMapping("/findCacheAdmin")
	public User findCacheAdmin(){
		User cacheSeller = userService.findCacheUser();
		if (cacheSeller == null){
			return null;
		}
		return cacheSeller;
	}

	@RequestMapping("/updatePassword")
	public Result updatePassword(String oldPassWord,String newPassWord1,String sellerId){
		try {
			Seller cacheSeller = findCacheSeller();
			if (oldPassWord.equals(cacheSeller.getPassword())){
				sellerService.updatePassword(newPassWord1,sellerId);
			}
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}

}
