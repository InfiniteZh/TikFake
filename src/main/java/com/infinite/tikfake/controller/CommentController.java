package com.infinite.tikfake.controller;

import com.infinite.tikfake.common.AjaxResult;
import com.infinite.tikfake.entity.Comment;
import com.infinite.tikfake.entity.User;
import com.infinite.tikfake.service.CommentService;
import com.infinite.tikfake.service.UserService;
import com.infinite.tikfake.service.VideoService;
import com.infinite.tikfake.utils.JwtUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;

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
                        String comment_text,
                        Integer comment_id){
        String username = JwtUtil.getUsername(token);
        User user = userService.getUserByName(username);
        Comment comment = new Comment();
        if (action_type == 1) {
            comment = commentService.postComment(video_id, comment_text, user);
        } else if (action_type == 2) {
            commentService.deleteComment(comment_id);
            return AjaxResult.success();
        }
        AjaxResult ajax = AjaxResult.success();
        ajax.put("comment", comment);
        return ajax;
    }

    @GetMapping("/list")
    AjaxResult getCommentList(@RequestParam("token") String token,
                              @RequestParam("video_id") Integer video_id){
        List<Comment> comments = commentService.getCommentByVideoId(video_id);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("comment_list", comments);
        return ajax;
    }
}
