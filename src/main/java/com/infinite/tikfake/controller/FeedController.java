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

import java.util.List;

@RestController
@Api("视频流接口")
@RequestMapping("/douyin")
public class FeedController {
    @Autowired
    VideoService videoService;

    @GetMapping("/feed")
    @ApiOperation("获取视频流")
    public AjaxResult feed(String latest_time, String token) {
        AjaxResult ajax;
        ajax = AjaxResult.success();
        List<Video> list = videoService.getVideoOrderByCreateTime(null);
        ajax.put("next_time", list.size() != 0 ? list.get(0).getCreateTime() : null);
        ajax.put("video_list", list);
        return ajax;
    }
}
