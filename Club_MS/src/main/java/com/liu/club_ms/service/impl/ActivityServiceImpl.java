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

    @Override
    public PageInfo<Activity> queryActivityList(int page, int limit,
                                                String name,
                                                String creatorName,
                                                Integer clubId,
                                                Integer captainId) {
        PageHelper.startPage(page, limit);
        List<Activity> activityList = activityMapper.queryActivityInfoList(name,
                creatorName,
                clubId,
                captainId);
        return new PageInfo<>(activityList);
    }

    @Override
    public boolean editActivityStatusById(Integer status, Integer activityId) {
        int num  = activityMapper.editStatusById(status, activityId);
        return num > 0;
    }

    @Override
    public boolean addActivity(Activity activity) {
        int num  = activityMapper.addActivity(activity);
        return num > 0;
    }

    @Override
    public boolean deleteById(Integer activityId) {
        int num  = activityMapper.deleteById(activityId);
        return num > 0;
    }
}
