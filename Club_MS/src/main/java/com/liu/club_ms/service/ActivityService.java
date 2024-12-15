package com.liu.club_ms.service;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.model.Activity;

public interface ActivityService {
    // 高级查询
    PageInfo<Activity> queryActivityList(int page, int limit,
                                         String name,
                                         String creatorName,
                                         Integer clubId,
                                         Integer captainId);

    //编辑活动状态
    boolean editActivityStatusById(Integer status, Integer activityId);

    boolean addActivity(Activity activity);

    boolean deleteById(Integer activityId);
}
