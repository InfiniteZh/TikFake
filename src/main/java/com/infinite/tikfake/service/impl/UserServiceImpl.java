package com.infinite.tikfake.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.infinite.tikfake.common.AjaxResult;
import com.infinite.tikfake.entity.User;
import com.infinite.tikfake.mapper.UserMapper;
import com.infinite.tikfake.service.UserService;
import com.infinite.tikfake.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserByName(String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.selectById(1);
    }

    @Override
    public void save(User user) {
        String md5Hash = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Hash);
        userMapper.insert(user);
    }

    @Override
    public AjaxResult register(String username, String password) {
        if(getUserByName(username) != null){
            return AjaxResult.error("用户已存在");
        }
        User user = new User(username, password);
        save(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user_id", user.getId());
        String token = JwtUtil.createJwtTokenByUser(user);
        ajax.put("token", token);
        return ajax;
    }

    @Override
    public AjaxResult login(String username, String password) {
        if(getUserByName(username) == null){
            return AjaxResult.error("用户不存在");
        }
        User user = getUserByName(username);
        String md5Hash = DigestUtils.md5DigestAsHex(password.getBytes());
        if(!md5Hash.equalsIgnoreCase(user.getPassword())){
            return AjaxResult.error("密码错误");
        }
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user_id", user.getId());
        String token = JwtUtil.createJwtTokenByUser(user);
        ajax.put("token", token);
        return ajax;
    }


}
