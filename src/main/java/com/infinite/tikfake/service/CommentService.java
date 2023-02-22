package com.infinite.tikfake.service;



public interface CommentService {

    String postComment(Integer videoId, String commentText);

    String deleteComment(Integer commentId);
}
