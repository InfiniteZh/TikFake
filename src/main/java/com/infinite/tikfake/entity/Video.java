package com.infinite.tikfake.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(excludeProperty = "author")
//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Video {
    private Integer id;
    @JsonIgnore
    private Integer userId;
    private User author;
    private String playUrl;
    private String coverUrl;
    private Integer favoriteCount;

    private Integer commentCount;
    private Boolean isFavorite;
    private String title;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;

    public Video(Integer userId, User author, String playUrl, String coverUrl, String title){
        this.userId = userId;
        this.author = author;
        this.playUrl = playUrl;
        this.coverUrl = coverUrl;
        this.title =title;
        this.favoriteCount = 0;
        this.commentCount = 0;
        this.isFavorite = false;
    }
}
