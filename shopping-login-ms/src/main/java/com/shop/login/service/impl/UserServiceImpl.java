package com.shop.login.service.impl;

import com.shop.login.service.UserService;
import com.shop.mapper.UserMapper;
import com.shop.pojo.User;
import com.shop.pojo.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String userName, String passWord) {

        User user = new User();
        user.setName(userName);
        user.setPassword(passWord);
        return userMapper.selectByUserNamePassWord(user);
    }
}
