package com.infinite.tikfake.service.impl;

import com.infinite.tikfake.common.RedisCom;
import com.infinite.tikfake.entity.Favorite;
import com.infinite.tikfake.service.RedisService;
import com.infinite.tikfake.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    RedisUtil redisUtil;


    @Override
    public void saveLikedRecord(Integer userId, Integer videoId) {
        String key = RedisCom.getLikedKey(userId, videoId);
        redisUtil.hPut(RedisCom.MAP_KEY_USER_LIKED, key, RedisCom.LIKE);
    }

    @Override
    public void saveUnlikeRecord(Integer userId, Integer videoId) {
        String key = RedisCom.getLikedKey(userId, videoId);
        redisUtil.hPut(RedisCom.MAP_KEY_USER_LIKED, key, RedisCom.UNLIKE);
    }

    @Override
    public void deleteLikedRecord(Integer userId, Integer videoId) {
        String key = RedisCom.getLikedKey(userId, videoId);
        redisUtil.hDelete(RedisCom.MAP_KEY_USER_LIKED, key);
    }

    @Override
    public void incrLikedCount(Integer videoId) {
        redisUtil.hIncrBy(RedisCom.MAP_KEY_USER_LIKED_COUNT, String.valueOf(videoId), 1);
    }

    @Override
    public void decrLikedCount(Integer videoId) {
        redisUtil.hIncrBy(RedisCom.MAP_KEY_USER_LIKED_COUNT, String.valueOf(videoId), -1);
    }

    @Override
    public void decrCommentCount(Integer commentId) {
        redisUtil.hIncrBy(RedisCom.MAP_KEY_COMMENT_COUNT, String.valueOf(commentId), -1);
    }

    @Override
    public void incrCommentCount(Integer commentId) {
        redisUtil.hIncrBy(RedisCom.MAP_KEY_COMMENT_COUNT, String.valueOf(commentId), 1);
    }

    @Override
    public List<Favorite> getLikedData() {
        Cursor<Map.Entry<Object, Object>> cursor = redisUtil.scan(RedisCom.MAP_KEY_USER_LIKED, ScanOptions.NONE);
        List<Favorite> list = new ArrayList<>();
        while(cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            //分离出 likedUserId，likedPostId
            String[] split = key.split("::");
            Integer userId = Integer.parseInt(split[0]);
            Integer videoId = Integer.parseInt(split[1]);
            Integer value = (Integer) entry.getValue();

            //组装成 UserLike 对象
            Favorite favorite = new Favorite(userId, videoId, value);
            list.add(favorite);
            //存到 list 后从 Redis 中删除
            redisUtil.hDelete(RedisCom.MAP_KEY_USER_LIKED, key);
        }
        return list;
    }

    @Override
    public Map<String, Integer> getLikedCount() {
        Cursor<Map.Entry<Object, Object>> cursor = redisUtil.scan(RedisCom.MAP_KEY_USER_LIKED_COUNT, ScanOptions.NONE);
        Map<String, Integer> res = new HashMap<>();
        while(cursor.hasNext()){
            Map.Entry<Object, Object> map = cursor.next();
            String key = (String)map.getKey();
            Integer val = (Integer) map.getValue();
            res.put(key, val);
            redisUtil.hDelete(RedisCom.MAP_KEY_USER_LIKED_COUNT, key);
        }
        return res;
    }

    @Override
    public Map<String, Integer> getCommentCount() {
        Cursor<Map.Entry<Object, Object>> cursor = redisUtil.scan(RedisCom.MAP_KEY_COMMENT_COUNT, ScanOptions.NONE);
        Map<String, Integer> res = new HashMap<>();
        while(cursor.hasNext()){
            Map.Entry<Object, Object> map = cursor.next();
            String key = (String)map.getKey();
            Integer val = (Integer) map.getValue();
            res.put(key, val);
            redisUtil.hDelete(RedisCom.MAP_KEY_COMMENT_COUNT, key);
        }
        return res;
    }

    @Override
    public Integer getLikedCountByVideoId(Integer videoId) {
        if(!redisUtil.hExists(RedisCom.MAP_KEY_USER_LIKED_COUNT, String.valueOf(videoId))){
            return 0;
        }
        return (Integer) redisUtil.hGet(RedisCom.MAP_KEY_USER_LIKED_COUNT, String.valueOf(videoId));
    }

    @Override
    public Integer getCommentCountByVideoId(Integer videoId) {
        if(!redisUtil.hExists(RedisCom.MAP_KEY_COMMENT_COUNT, String.valueOf(videoId))){
            return 0;
        }
        return (Integer) redisUtil.hGet(RedisCom.MAP_KEY_COMMENT_COUNT, String.valueOf(videoId));
    }
}
