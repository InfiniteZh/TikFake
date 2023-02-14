package com.infinite.tikfake.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.infinite.tikfake.entity.User;
import com.infinite.tikfake.mapper.UserMapper;
import com.infinite.tikfake.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserByName(String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    @Override
    public User getUserById(Integer id) {
        User user = userMapper.selectById(1);
        return user;
    }

    @Override
    public void save(User user) {
        userMapper.insert(user);
    }



}
