package com.infinite.tikfake.common;

public class RedisCom {
    //保存用户点赞数据的key
    public static final String MAP_KEY_USER_LIKED = "MAP_USER_LIKED";
    //保存用户被点赞数量的key
    public static final String MAP_KEY_USER_LIKED_COUNT = "MAP_USER_LIKED_COUNT";

    public static final Integer LIKE = 1;
    public static final Integer UNLIKE = 0;
    /**
     * 拼接被点赞的用户id和点赞的人的id作为key。格式 222222::333333
     * @param userId  点赞用户id
     * @param videoId 被点赞视频id
     * @return
     */
    public static String getLikedKey(Integer userId, Integer videoId){
        StringBuilder builder = new StringBuilder();
        builder.append(String.valueOf(userId));
        builder.append("::");
        builder.append(String.valueOf(videoId));
        return builder.toString();
    }
}