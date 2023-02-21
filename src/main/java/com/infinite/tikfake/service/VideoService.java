package com.infinite.tikfake.service;

import com.infinite.tikfake.entity.Video;
import org.springframework.web.multipart.MultipartFile;

public interface VideoService {
    Video getVideoDemo();

    void saveVideo(MultipartFile data, String token, String title);
}
