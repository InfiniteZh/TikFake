package com.infinite.tikfake.service;


import com.infinite.tikfake.entity.Comment;
import com.infinite.tikfake.entity.User;

import java.util.List;

public interface CommentService {

    Comment postComment(Integer videoId, String commentText, User user);

    void deleteComment(Integer commentId);

    void transCommentCountFromRedis2DB();

    List<Comment> getCommentByVideoId(Integer videoId);
}
