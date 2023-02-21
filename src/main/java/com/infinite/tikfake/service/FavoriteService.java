package com.infinite.tikfake.service;



public interface FavoriteService {

    void favorite(Integer userId, Integer videoId);
    void notFavorite(Integer userId, Integer videoId);
}
