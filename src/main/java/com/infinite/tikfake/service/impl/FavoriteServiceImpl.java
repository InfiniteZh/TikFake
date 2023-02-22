package com.infinite.tikfake.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.infinite.tikfake.entity.Favorite;
import com.infinite.tikfake.entity.Video;
import com.infinite.tikfake.mapper.FavoriteMapper;
import com.infinite.tikfake.mapper.VideoMapper;
import com.infinite.tikfake.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    FavoriteMapper favoriteMapper;
    @Autowired
    VideoMapper videoMapper;

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

    @Override
    public List<Video> getFavoriteList(Integer userId) {
        QueryWrapper<Favorite> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        List<Favorite> favorite = favoriteMapper.selectList(wrapper);
        List<Video> favoriteVideo = new ArrayList<>();
        for (Favorite f : favorite) {
            Integer video_id = f.getVideoId();
            QueryWrapper<Video> Videowrapper = new QueryWrapper<>();
            Videowrapper.eq("id", video_id);
            favoriteVideo.add(videoMapper.selectOne(Videowrapper));
        }
        return favoriteVideo;
    }
}
