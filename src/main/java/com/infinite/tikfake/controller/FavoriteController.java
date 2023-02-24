package com.infinite.tikfake.controller;

import com.infinite.tikfake.common.AjaxResult;
import com.infinite.tikfake.entity.User;
import com.infinite.tikfake.service.FavoriteService;
import com.infinite.tikfake.service.UserService;
import com.infinite.tikfake.service.VideoService;
import com.infinite.tikfake.utils.JwtUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api("点赞Controller")
@RestController
@RequestMapping("/douyin/favorite")
public class FavoriteController {
    @Autowired
    FavoriteService favoriteService;
    @Autowired
    UserService userService;
    @Autowired
    VideoService videoService;

    @PostMapping("/action")
    AjaxResult favorite(@RequestParam("token") String token,
                        @RequestParam("video_id") Integer video_id,
                        @RequestParam("action_type") Integer action_type){
        String username = JwtUtil.getUsername(token);
        User user = userService.getUserByName(username);
        if(action_type == 1) {
            favoriteService.favorite(user.getId(), video_id);
        } else if (action_type == 2) {
            favoriteService.notFavorite(user.getId(), video_id);
        }
        return AjaxResult.success();
    }

    @GetMapping("/list")
    AjaxResult getFavoriteList(@RequestParam("user_id") Integer user_id,
                               @RequestParam("token") String token) {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("video_list", favoriteService.getFavoriteList(user_id));
        return ajax;
    }
}
