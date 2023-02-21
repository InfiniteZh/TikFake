package com.infinite.tikfake.service.impl;

import com.infinite.tikfake.entity.Favorite;
import com.infinite.tikfake.mapper.FavoriteMapper;
import com.infinite.tikfake.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    FavoriteMapper favoriteMapper;

    @Override
    public void favorite(Integer userId, Integer videoId) {
        Favorite favorite = new Favorite(userId, videoId);
        favoriteMapper.insert(favorite);
    }

    @Override
    public void notFavorite(Integer userId, Integer videoId) {
        //根据map集合中所设置的条件删除用户信息
        Map<String,Object> DeleteMap = new HashMap<>();
        DeleteMap.put("user_id",userId);
        DeleteMap.put("video_id",videoId);
        favoriteMapper.deleteByMap(DeleteMap);
    }
}
