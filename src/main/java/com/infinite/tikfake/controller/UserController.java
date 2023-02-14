package com.infinite.tikfake.controller;

import com.infinite.tikfake.entity.User;
import com.infinite.tikfake.common.AjaxResult;
import com.infinite.tikfake.service.UserService;
import com.infinite.tikfake.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/douyin")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RedisUtil redisUtil;
    @GetMapping("user")
    AjaxResult getUserInfo(@RequestParam("user_id") String user_id,
                           @RequestParam("token") String token){
        AjaxResult ajax;
        if(redisUtil.hasKey(token)){
            User user = (User) redisUtil.get(token);
            ajax = AjaxResult.success();
            ajax.put("user", user);
        }
        else {
            ajax = AjaxResult.error("用户未登录");
        }
        return ajax;
    }


    @PostMapping("user/register")
    AjaxResult register(@RequestParam("username") String username,
                        @RequestParam("password") String password){
        AjaxResult ajax;
        String token = username + password;
        if(redisUtil.hasKey(token) || userService.getUserByName(username) != null){
            ajax = AjaxResult.error("用戶已存在");
        }
        else{
            User user = new User(username, password);
            userService.save(user);
            redisUtil.set(token, user);
            ajax = AjaxResult.success();
            ajax.put("user_id", user.getId());
            ajax.put("token", token);
        }
        return ajax;
    }

    @PostMapping("user/login")
    AjaxResult login(@RequestParam("username") String username,
                     @RequestParam("password") String password){
        String token = username + password;
        AjaxResult ajax;
        if(redisUtil.hasKey(token)){
            User user = (User) redisUtil.get(token);
            ajax = AjaxResult.success();
            ajax.put("user_id", user.getId());
            ajax.put("token", token);
        }
        else{
            ajax = AjaxResult.error("未登录");
        }
        return ajax;
    }


}
