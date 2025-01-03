package com.liu.club_ms.mapper;

import com.liu.club_ms.model.MeetingInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetingInfoMapper {

    // 分页查询或者高级查询会议记录
    List<MeetingInfo> queryMeetingInfoList(String realName,
                                           String name,
                                           Integer userId,
                                           String clubName,
                                           String order);

    // 编辑会议记录
    Integer editMeetingInfo(MeetingInfo meetingInfo);

    // 添加会议记录
    Integer addMeetingInfo(MeetingInfo meetingInfo);

    // 通过ID删除会议记录
    Integer deleteById(Integer miId);

    // 通过社团ID删除会议记录
    Integer deleteMeetingInfoByClubId(Integer clubId);

    // 通过已删除的用户ID更新会议记录
    Integer updateMeetingInfoWhenDeleteUser(Integer userId);

    // 通过用户ID获取社团会议信息
    List<MeetingInfo> queryMeetingInfoListByUserId(Integer userId, String curTime);

    // 通过会议Id获取会议信息
    MeetingInfo getMeetingInfoById(Integer miId, String curTime);

    // 通过会议名称获取会议信息
    MeetingInfo getMeetingInfoByName(String name, String curTime);

    // 通过社团ID更新创建者ID
    Integer updateMeetingInfoUserIdByClubId(Integer clubId, Integer userId);
}
