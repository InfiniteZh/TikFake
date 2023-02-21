package com.infinite.tikfake.controller;

import com.infinite.tikfake.common.AjaxResult;
import com.infinite.tikfake.service.VideoService;
import com.infinite.tikfake.utils.JwtUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Api("视频发布Controller")
@RestController
@RequestMapping("/douyin/publish")
public class PublishController {

    @Autowired
    VideoService videoService;

    @PostMapping("/action")
    public AjaxResult publish_action(@RequestPart("data") MultipartFile data,
                                     @RequestParam("token") String token,
                                     @RequestParam("title") String title) {
        if(!JwtUtil.verifyTokenOfUser(token)){
            return AjaxResult.error("token error");
        }
        AjaxResult ajax;

        videoService.saveVideo(data, token, title);

        ajax = AjaxResult.success();
        return ajax;
    }
}
