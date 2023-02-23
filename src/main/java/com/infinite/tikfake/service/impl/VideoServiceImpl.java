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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
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
    @Transactional
    public Video getVideoDemo() {
        Video video = videoMapper.selectById(1);
        User user = userService.getUserById(video.getUserId());
        video.setAuthor(user);
        return video;
    }

    @Override
    @Transactional
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
    @Transactional
    public Video getVideoByTitle(String title) {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("title", title);
        return videoMapper.selectOne(wrapper);
    }

    @Override
    @Transactional
    public List<Video> getVideoOrderByCreateTime(String latest_time) {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.orderBy(true, false, "create_time");
        if(latest_time != null)
            wrapper.ge("create_time", new Date(Long.parseLong(latest_time)));
        wrapper.last("limit 30");
        List<Video> videos = videoMapper.selectList(wrapper);
        for(Video video : videos){
            video.setPlayUrl(videoUtil.getQiniuURL(video.getPlayUrl()));
            video.setCoverUrl(videoUtil.getQiniuURL(video.getCoverUrl()));
        }
        return videos;
    }

    @Override
    @Transactional
    public List<Video> getVideoByUser(Integer user_id) {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", user_id);
        wrapper.last("limit 10");
        return videoMapper.selectList(wrapper);
    }

    @Override
    public Video getVideoByVideoId(Integer videoId) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", videoId);
        return videoMapper.selectOne(queryWrapper);
    }
}
