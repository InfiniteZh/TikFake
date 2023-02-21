package com.infinite.tikfake.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.infinite.tikfake.entity.User;
import com.infinite.tikfake.entity.Video;
import com.infinite.tikfake.mapper.VideoMapper;
import com.infinite.tikfake.service.UserService;
import com.infinite.tikfake.service.VideoService;
import com.infinite.tikfake.utils.JwtUtil;
import com.infinite.tikfake.utils.VideoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    VideoMapper videoMapper;
    @Autowired
    UserService userService;

    @Autowired
    VideoUtil videoUtil;

    @Value("${qiniu.domain}")
    private String domain;

    @Override
    public Video getVideoDemo() {
        Video video = videoMapper.selectById(1);
        User user = userService.getUserById(video.getUserId());
        video.setAuthor(user);
        return video;
    }

    @Override
    public void saveVideo(MultipartFile data,  String token, String title) {
        String keyVideo  = "video/" + title + ".mp4";
        String keyImg  = "cover/" + title + ".jpg";
        videoUtil.saveVideoOnline(data, keyVideo, keyImg);
        String playUrl = domain + keyVideo;
        String coverUrl = domain + keyImg;
        String username = JwtUtil.getUsername(token);
        User user = userService.getUserByName(username);
        Video video = new Video(user.getId(), user, playUrl, coverUrl, title);
        videoMapper.insert(video);
    }

    @Override
    public Video getVideoByTitle(String title) {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("title", title);
        return videoMapper.selectOne(wrapper);
    }

    @Override
    public List<Video> getVideoOrderByCreateTime() {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.orderBy(true, false, "create_time");
        wrapper.last("limit 30");
        List<Video> videos = videoMapper.selectList(wrapper);
        for(Video video : videos){
            video.setPlayUrl(videoUtil.getQiniuURL(video.getPlayUrl()));
            video.setCoverUrl(videoUtil.getQiniuURL(video.getCoverUrl()));
        }
        return videos;
    }
}
