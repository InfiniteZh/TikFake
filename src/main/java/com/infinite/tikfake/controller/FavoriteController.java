package com.infinite.tikfake.controller;

import com.infinite.tikfake.common.AjaxResult;
import com.infinite.tikfake.entity.User;
import com.infinite.tikfake.service.FavoriteService;
import com.infinite.tikfake.service.UserService;
import com.infinite.tikfake.utils.JwtUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Api("点赞Controller")
@RestController
@RequestMapping("/douyin/favorite")
public class FavoriteController {
    @Autowired
    FavoriteService favoriteService;
    @Autowired
    UserService userService;

    @PostMapping("/action")
    AjaxResult favorite(@RequestParam("token") String token,
                        @RequestParam("videoId") Integer video_id,
                        @RequestParam("actionType") Integer action_type){
        if(!JwtUtil.verifyTokenOfUser(token)){
            return AjaxResult.error("请先登录！");
        }
        String username = JwtUtil.getUsername(token);
        User user = userService.getUserByName(username);
        if(action_type == 1) {
            favoriteService.favorite(user.getId(), video_id);
        } else if (action_type == 2) {
            favoriteService.notFavorite(user.getId(), video_id);
        }
        return AjaxResult.success();

    }
}
