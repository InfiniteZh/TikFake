package com.infinite.tikfake.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class User {
    private Integer id;
    private String name;
    @JsonIgnore
    private String password;
    private Integer followCount;
    private Integer followerCount;
    private Boolean isFollow;

    public User(String name, String password){
        this.name = name;
        this.password = password;
        this.followCount = 0;
        this.followerCount = 0;
        this.isFollow = false;
    }
}
