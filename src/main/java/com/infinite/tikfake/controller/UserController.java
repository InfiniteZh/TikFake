package com.infinite.tikfake.controller;

import com.infinite.tikfake.entity.User;
import com.infinite.tikfake.common.AjaxResult;
import com.infinite.tikfake.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/douyin")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("user")
    AjaxResult getUserInfo(@RequestParam("user_id") Integer user_id,
                           @RequestParam("token") String token){
        User user = userService.getUserById(user_id);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        return ajax;
    }


    @PostMapping("user/register")
    AjaxResult register(@RequestParam("username") String username,
                        @RequestParam("password") String password){
        return userService.register(username, password);
    }

    @PostMapping("user/login")
    AjaxResult login(@RequestParam("username") String username,
                     @RequestParam("password") String password){
        return userService.login(username, password);
    }


}
