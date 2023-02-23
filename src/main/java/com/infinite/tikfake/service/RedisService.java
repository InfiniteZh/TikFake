package com.infinite.tikfake.service;

import com.infinite.tikfake.entity.Favorite;

import java.util.List;
import java.util.Map;

public interface RedisService {
    void saveLikedRecord(Integer userId, Integer videoId);

    void saveUnlikeRecord(Integer userId, Integer videoId);

    void deleteLikedRecord(Integer userId, Integer videoId);

    void incrLikedCount(Integer videoId);

    void decrLikedCount(Integer videoId);

    List<Favorite> getLikedData();

    Map<String, Integer> getLikedCount();
}
