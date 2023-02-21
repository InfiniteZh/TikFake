package com.infinite.tikfake.controller;

import com.infinite.tikfake.entity.Video;
import com.infinite.tikfake.common.AjaxResult;
import com.infinite.tikfake.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@Api("视频流接口")
@RequestMapping("/douyin")
public class FeedController {
    @Autowired
    VideoService videoService;
    @GetMapping("/feed")
    @ApiOperation("获取视频流")
    public AjaxResult Feed() {
        List<Video> list = videoService.getVideoOrderByCreateTime();
        AjaxResult ajax = AjaxResult.success();
        ajax.put("next_time", new Date(System.currentTimeMillis()));
        ajax.put("video_list", list);
        return ajax;
    }
}
