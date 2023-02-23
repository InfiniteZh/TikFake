package com.infinite.tikfake.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.infinite.tikfake.entity.Comment;
import com.infinite.tikfake.entity.User;
import com.infinite.tikfake.entity.Video;
import com.infinite.tikfake.mapper.CommentMapper;
import com.infinite.tikfake.mapper.VideoMapper;
import com.infinite.tikfake.service.CommentService;
import com.infinite.tikfake.service.RedisService;
import com.infinite.tikfake.service.UserService;
import com.infinite.tikfake.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    VideoService videoService;
    @Autowired
    VideoMapper videoMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    UserService userService;

    @Override
    @Transactional
    public Comment postComment(Integer videoId, String commentText, User user) {
        Comment comment = new Comment(videoId, user.getId(), commentText, user, new Date(System.currentTimeMillis()));
        commentMapper.insert(comment);
        redisService.incrCommentCount(comment.getCommentId());
        return comment;
    }

    @Override
    @Transactional
    public void deleteComment(Integer commentId) {
        commentMapper.deleteById(commentId);
        redisService.decrCommentCount(commentId);
    }

    @Override
    public void transCommentCountFromRedis2DB() {
        Map<String, Integer> map = redisService.getCommentCount();
        for (String commentId : map.keySet()) {
            Video video = videoService.getVideoByVideoId(Integer.parseInt(commentId));
            //点赞数量属于无关紧要的操作，出错无需抛异常
            if (video != null){
                Integer likeNum = video.getCommentCount() + map.get(commentId);
                video.setFavoriteCount(likeNum);
                //更新点赞数量
                videoMapper.updateById(video);
            }
        }
    }

    @Override
    public List<Comment> getCommentByVideoId(Integer videoId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("video_id", videoId);
        List<Comment> comments = commentMapper.selectList(wrapper);
        for(Comment comment : comments){
            comment.setUser(userService.getUserById(comment.getUserId()));
        }
        return comments;
    }
}
