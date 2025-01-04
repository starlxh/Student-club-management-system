package com.liu.club_ms.service;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.model.MeetingInfo;

import java.util.List;

public interface MeetingInfoService {

    // 分页查询或者高级查询会议记录
    PageInfo<MeetingInfo> queryMeetingInfoList(int page, int limit,
                                          String realName,
                                          String name,
                                          Integer userId,
                                          String clubName,
                                               String order);

    // 编辑会议记录
    boolean editMeetingInfo(MeetingInfo meetingInfo);

    // 添加会议记录
    boolean addMeetingInfo(MeetingInfo meetingInfo);

    // 通过ID删除会议记录
    boolean deleteById(Integer meetingInfoId);

    // 通过用户ID获取社团会议信息
    List<MeetingInfo> queryMeetingInfoListByUserId(Integer userId);

    // 通过会议Id获取会议信息
    MeetingInfo getMeetingInfoById(Integer miId);

    // 通过会议名称获取会议信息
    MeetingInfo getMeetingInfoByName(String name);
}
