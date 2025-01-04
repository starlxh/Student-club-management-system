package com.liu.club_ms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.club_ms.mapper.ActivityMapper;
import com.liu.club_ms.model.Activity;
import com.liu.club_ms.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    ActivityMapper activityMapper;

    // 分页查询或高级查询活动信息
    @Override
    public PageInfo<Activity> queryActivityList(int page, int limit,
                                                String name,
                                                String creatorName,
                                                Integer clubId,
                                                Integer captainId,
                                                String order) {
        PageHelper.startPage(page, limit);
        List<Activity> activityList = activityMapper.queryActivityInfoList(name,
                creatorName,
                clubId,
                captainId,
                order);
        return new PageInfo<>(activityList);
    }

    // 编辑活动状态
    @Override
    public boolean editActivityStatusById(Integer status, Integer activityId) {
        return activityMapper.editStatusById(status, activityId) > 0;
    }

    // 编辑活动信息
    @Override
    public boolean editActivity(Activity activity) {
        return activityMapper.editActivity(activity) > 0;
    }

    // 添加活动
    @Override
    public boolean addActivity(Activity activity) {
        return activityMapper.addActivity(activity) > 0;
    }

    // 通过ID删除活动
    @Override
    public boolean deleteById(Integer activityId) {
        return activityMapper.deleteById(activityId) > 0;
    }

    // 通过用户ID查询社团活动
    @Override
    public List<Activity> queryActivityListByUserId(Integer userId) {
        return activityMapper.queryActivityListByUserId(userId);
    }

    // 通过活动ID查询活动信息
    @Override
    public Activity queryActivityListById(Integer activityId) {
        return activityMapper.queryActivityListById(activityId);
    }
}
