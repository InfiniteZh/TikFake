package com.infinite.tikfake.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(excludeProperty = "user")
public class Comment {

    @TableId("comment_id")
    private Integer commentId;
    private Integer videoId;
    @JsonIgnore
    private Integer userId;
    private User user;
    private String commentText;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public Comment(Integer videoId, Integer userId, String commentText, User user, Date createTime) {
        this.videoId = videoId;
        this.userId = userId;
        this.commentText = commentText;
        this.user = user;
        this.createTime = createTime;
    }
}