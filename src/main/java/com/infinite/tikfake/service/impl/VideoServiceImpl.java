package com.infinite.tikfake.service.impl;

import com.infinite.tikfake.entity.User;
import com.infinite.tikfake.entity.Video;
import com.infinite.tikfake.mapper.VideoMapper;
import com.infinite.tikfake.service.UserService;
import com.infinite.tikfake.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    VideoMapper videoMapper;
    @Autowired
    UserService userService;
    @Override
    public Video getVideoDemo() {
        Video video = videoMapper.selectById(1);
        User user = userService.getUserById(video.getUserId());
        video.setAuthor(user);
        return video;
    }
}
