package com.infinite.tikfake.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Favorite {
    private Integer id;
    private Integer userId;
    private Integer videoId;

    private Integer status;

    public Favorite(Integer userId, Integer videoId, Integer status){
        this.userId = userId;
        this.videoId = videoId;
        this.status = status;
    }

}
