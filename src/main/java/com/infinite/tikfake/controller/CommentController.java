package com.infinite.tikfake.controller;

import com.infinite.tikfake.common.AjaxResult;
import com.infinite.tikfake.entity.User;
import com.infinite.tikfake.service.CommentService;
import com.infinite.tikfake.service.UserService;
import com.infinite.tikfake.service.VideoService;
import com.infinite.tikfake.utils.JwtUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("评论Controller")
@RestController
@RequestMapping("/douyin/comment")
public class CommentController {

    @Autowired
    UserService userService;
    @Autowired
    VideoService videoService;
    @Autowired
    CommentService commentService;

    @PostMapping("/action")
    AjaxResult favorite(@RequestParam("token") String token,
                        @RequestParam("video_id") Integer video_id,
                        @RequestParam("action_type") Integer action_type,
                        @RequestParam("comment_text") String comment_text,
                        @RequestParam("comment_id") Integer comment_id){
        String username = JwtUtil.getUsername(token);
        User user = userService.getUserByName(username);
        String createDate = null;
        if (action_type == 1) {
            createDate = commentService.postComment(video_id, comment_text);
        } else if (action_type == 2) {
            createDate = commentService.deleteComment(comment_id);
        }
        AjaxResult ajax = AjaxResult.success();
        ajax.put("id", comment_id);
        ajax.put("user", user);
        ajax.put("content", comment_text);
        ajax.put("create_date", createDate);
        return ajax;
    }
}
