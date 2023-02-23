package com.infinite.tikfake.service;

import com.infinite.tikfake.entity.Video;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    Video getVideoDemo();

    void saveVideo(MultipartFile data, String token, String title);

    Video getVideoByTitle(String title);

    List<Video> getVideoOrderByCreateTime(String latest_time);

    List<Video> getVideoByUser(Integer user_id);

    Video getVideoByVideoId(Integer videoId);

}
