package com.infinite.tikfake.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Integer videoId;
    @TableId("comment_id")
    private Integer commentId;
    private String commentText;
    @JsonIgnore
    private String createDate;
    public Comment(Integer videoId, String commentText) {
        this.videoId = videoId;
        this.commentText = commentText;
    }
}