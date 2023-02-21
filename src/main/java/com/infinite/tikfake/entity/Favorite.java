package com.infinite.tikfake.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Favorite {
    private Integer userId;
    private Integer videoId;

}
