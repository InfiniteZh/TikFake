package com.infinite.tikfake.controller;

import com.infinite.tikfake.common.AjaxResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Api("视频发布Controller")
@RestController
@RequestMapping("/douyin/publish")
public class PublishController {

    @PostMapping("/action")
    public AjaxResult publish_action(@RequestPart("data") MultipartFile data,
                                     @RequestParam("token") String token,
                                     @RequestParam("title") String title) throws IOException {
        AjaxResult ajax;

        if(!data.isEmpty()){
            String file_name = data.getOriginalFilename();
            data.transferTo(new File("C:\\Users\\11930\\Desktop\\Temp\\" + file_name));
            ajax = AjaxResult.success();
        }
        else{
            ajax = AjaxResult.error("视频为空");
        }
        return ajax;
    }
}
