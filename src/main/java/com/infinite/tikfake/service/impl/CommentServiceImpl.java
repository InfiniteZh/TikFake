package com.infinite.tikfake.service.impl;

import com.infinite.tikfake.entity.Comment;
import com.infinite.tikfake.mapper.CommentMapper;
import com.infinite.tikfake.mapper.VideoMapper;
import com.infinite.tikfake.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    VideoMapper videoMapper;
    @Autowired
    CommentMapper commentMapper;

    @Override
    @Transactional
    public String postComment(Integer videoId, String commentText) {
        Comment comment = new Comment(videoId, commentText);
        commentMapper.insert(comment);

        return comment.getCreateDate();
    }

    @Override
    @Transactional
    public String deleteComment(Integer commentId) {
        commentMapper.deleteById(commentId);
        return null;
    }
}
