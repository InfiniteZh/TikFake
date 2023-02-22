package com.infinite.tikfake.controller;

import com.infinite.tikfake.common.AjaxResult;
import com.infinite.tikfake.entity.Video;
import com.infinite.tikfake.service.VideoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


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
        AjaxResult ajax;
        if(videoService.getVideoByTitle(title) != null){
            return AjaxResult.error("请换一个title");
        }
        videoService.saveVideo(data, token, title);

        ajax = AjaxResult.success();
        return ajax;
    }

    @GetMapping("list")
    public AjaxResult getPublishList(@RequestParam("user_id") Integer user_id,
                                     @RequestParam("token") String token) {
        List<Video> videos = videoService.getVideoByUser(user_id);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("video_list", videos);
        return ajax;
    }
}
