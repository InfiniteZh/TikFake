package com.infinite.tikfake.quartz;

import com.infinite.tikfake.service.CommentService;
import com.infinite.tikfake.service.FavoriteService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class FavoriteTask extends QuartzJobBean {

    @Autowired
    FavoriteService favoriteService;
    @Autowired
    CommentService commentService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {

        log.info("LikeTask-------- {}", sdf.format(new Date()));

        //将 Redis 里的点赞信息同步到数据库里
        favoriteService.transFavoriteFromRedis2DB();
        favoriteService.transFavoriteCountFromRedis2DB();
        commentService.transCommentCountFromRedis2DB();
    }
}