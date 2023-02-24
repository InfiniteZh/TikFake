package com.infinite.tikfake.service;


import com.infinite.tikfake.entity.Video;

import java.util.List;

public interface FavoriteService {

    void favorite(Integer userId, Integer videoId);
    void notFavorite(Integer userId, Integer videoId);

    List<Video> getFavoriteList(Integer userId);

    Boolean isFavoriteByUserIdAndVideoId(Integer videoId, Integer userId);

    void transFavoriteFromRedis2DB();
    void transFavoriteCountFromRedis2DB();

}
