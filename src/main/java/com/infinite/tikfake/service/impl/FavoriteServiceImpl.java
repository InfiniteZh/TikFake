package com.infinite.tikfake.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.infinite.tikfake.entity.Favorite;
import com.infinite.tikfake.entity.Video;
import com.infinite.tikfake.mapper.FavoriteMapper;
import com.infinite.tikfake.mapper.VideoMapper;
import com.infinite.tikfake.service.FavoriteService;
import com.infinite.tikfake.service.RedisService;
import com.infinite.tikfake.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    FavoriteMapper favoriteMapper;
    @Autowired
    VideoMapper videoMapper;

    @Autowired
    VideoService videoService;
    @Autowired
    RedisService redisService;

    @Override
    @Transactional
    public void favorite(Integer userId, Integer videoId) {
        redisService.saveLikedRecord(userId, videoId);
        redisService.incrLikedCount(videoId);
    }

    @Override
    @Transactional
    public void notFavorite(Integer userId, Integer videoId) {
        redisService.saveUnlikeRecord(userId, videoId);
        redisService.decrLikedCount(videoId);
    }

    @Override
    @Transactional
    public List<Video> getFavoriteList(Integer userId) {
        QueryWrapper<Favorite> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        List<Favorite> favorite = favoriteMapper.selectList(wrapper);
        List<Video> favoriteVideo = new ArrayList<>();
        for (Favorite f : favorite) {
            Integer video_id = f.getVideoId();
            QueryWrapper<Video> Videographer = new QueryWrapper<>();
            Videographer.eq("id", video_id);
            favoriteVideo.add(videoMapper.selectOne(Videographer));
        }
        return favoriteVideo;
    }

    @Override
    public Boolean isFavoriteByUserIdAndVideoId(Integer userId, Integer videoId) {
        QueryWrapper<Favorite> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("video_id", videoId);
        return favoriteMapper.selectOne(wrapper).getStatus() == 1;
    }

    public Favorite getByUserIdAndVideoId(Integer userId, Integer videoId){
        QueryWrapper<Favorite> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("video_id", videoId);
        return favoriteMapper.selectOne(wrapper);
    }
    @Override
    @Transactional
    public void transFavoriteFromRedis2DB() {
        List<Favorite> list = redisService.getLikedData();
        for (Favorite favorite : list) {
            Favorite ul = getByUserIdAndVideoId(favorite.getUserId(), favorite.getVideoId());
            if (ul == null){
                //没有记录，直接存入
                favoriteMapper.insert(favorite);
            }else{
                //有记录，需要更新
                ul.setStatus(favorite.getStatus());
                favoriteMapper.updateById(ul);
            }
        }
    }

    @Override
    @Transactional
    public void transFavoriteCountFromRedis2DB() {
        Map<String, Integer> map = redisService.getLikedCount();
        for (String videoId : map.keySet()) {
            Video video = videoService.getVideoByVideoId(Integer.parseInt(videoId));
            //点赞数量属于无关紧要的操作，出错无需抛异常
            if (video != null){
                Integer likeNum = video.getFavoriteCount() + map.get(videoId);
                video.setFavoriteCount(likeNum);
                //更新点赞数量
                videoMapper.updateById(video);
            }
        }
    }

}
