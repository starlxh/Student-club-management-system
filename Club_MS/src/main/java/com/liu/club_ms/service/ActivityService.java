package com.liu.club_ms.service;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.model.Activity;

import java.util.List;

public interface ActivityService {
    // 分页查询或高级查询活动信息
    PageInfo<Activity> queryActivityList(int page, int limit,
                                         String name,
                                         String creatorName,
                                         Integer clubId,
                                         Integer captainId,
                                         String order);

    // 编辑活动状态
    boolean editActivityStatusById(Integer status, Integer activityId);

    // 编辑活动信息
    boolean editActivity(Activity activity);

    // 添加活动
    boolean addActivity(Activity activity);

    // 通过ID删除活动
    boolean deleteById(Integer activityId);

    // 通过用户ID查询社团活动
    List<Activity> queryActivityListByUserId(Integer userId);

    // 通过活动ID查询活动信息
    Activity queryActivityListById(Integer activityId);
}
