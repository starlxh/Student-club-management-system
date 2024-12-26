package com.liu.club_ms.mapper;

import com.liu.club_ms.model.Activity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActivityMapper {
    // 分页查询或高级查询活动记录
    List<Activity> queryActivityInfoList(String name,
                                         String creatorName,
                                         Integer clubId,
                                         Integer captainId,
                                         String order);

    // 编辑活动状态
    Integer editStatusById(Integer status, Integer activityId);

    // 编辑活动信息
    Integer editActivity(Activity activity);

    // 添加活动记录
    Integer addActivity(Activity activity);

    // 通过ID删除活动记录
    Integer deleteById(Integer activityId);

    // 通过社团ID删除活动记录
    Integer deleteActivityByClubId(Integer clubId);

    // 更新活动记录当创建者被删除
    Integer updateActivityWhenUserDelete(Integer userId);

    // 更新活动记录当主持人被删除
    Integer updateActivityWhenHostDelete(Integer hostId);

    // 通过用户ID查询社团活动
    List<Activity> queryActivityListByUserId(Integer userId);

    // 通过活动ID查询活动信息
    Activity queryActivityListById(Integer activityId);


}
