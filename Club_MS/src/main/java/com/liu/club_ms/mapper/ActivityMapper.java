package com.liu.club_ms.mapper;

import com.liu.club_ms.model.Activity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActivityMapper {
    // 高级查询
    List<Activity> queryActivityInfoList(String name,
                                         String creatorName,
                                         Integer clubId,
                                         Integer captainId);

    //编辑活动状态
    Integer editStatusById(Integer status, Integer activityId);

    Integer addActivity(Activity activity);

    Integer deleteById(Integer activityId);
}
