package com.infinite.tikfake.service;

import com.infinite.tikfake.entity.Favorite;

import java.util.List;
import java.util.Map;

public interface RedisService {
    void saveLikedRecord(Integer userId, Integer videoId);

    void saveUnlikeRecord(Integer userId, Integer videoId);

    void deleteLikedRecord(Integer userId, Integer videoId);

    void incrLikedCount(Integer videoId);
    void incrCommentCount(Integer commentId);

    void decrLikedCount(Integer videoId);

    void decrCommentCount(Integer commentId);

    List<Favorite> getLikedData();

    Map<String, Integer> getLikedCount();
    Map<String, Integer> getCommentCount();

    Integer getLikedCountByVideoId(Integer videoId);

    Integer getCommentCountByVideoId(Integer videoId);

}
