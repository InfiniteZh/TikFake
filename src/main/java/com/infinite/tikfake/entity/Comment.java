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

    private Integer id;
    @JsonIgnore
    private Integer videoId;
    @JsonIgnore
    private Integer userId;
    private User user;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    public Comment(Integer videoId, Integer userId, String commentText, User user, Date createDate) {
        this.videoId = videoId;
        this.userId = userId;
        this.content = commentText;
        this.user = user;
        this.createDate = createDate;
    }
}