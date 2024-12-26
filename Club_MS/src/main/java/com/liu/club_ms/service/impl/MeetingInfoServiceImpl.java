package com.liu.club_ms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.club_ms.mapper.MeetingInfoMapper;
import com.liu.club_ms.model.MeetingInfo;
import com.liu.club_ms.service.MeetingInfoService;
import com.liu.club_ms.util.CompareTime;
import com.liu.club_ms.util.Date2Str;
import com.liu.club_ms.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MeetingInfoServiceImpl implements MeetingInfoService {

    @Autowired
    private MeetingInfoMapper meetingInfoMapper;

    // 分页查询或者高级查询公告
    @Override
    public PageInfo<MeetingInfo> queryMeetingInfoList(int page, int limit,
                                                      String realName,
                                                      String title,
                                                      Integer userId,
                                                      String clubName,
                                                      String order) {
        PageHelper.startPage(page, limit);
        List<MeetingInfo> list = meetingInfoMapper.queryMeetingInfoList(
                realName,
                title,
                userId,
                clubName,
                order);
        return new PageInfo<>(list);
    }

    // 编辑会议记录
    @Override
    public boolean editMeetingInfo(MeetingInfo meetingInfo) {
        return meetingInfoMapper.editMeetingInfo(meetingInfo) > 0;
    }

    // 添加会议记录
    @Override
    public boolean addMeetingInfo(MeetingInfo meetingInfo) {
        return meetingInfoMapper.addMeetingInfo(meetingInfo) > 0;
    }

    // 通过ID删除会议记录
    @Override
    public boolean deleteById(Integer miId) {
        return meetingInfoMapper.deleteById(miId) > 0;
    }

    // 通过用户ID获取社团会议信息
    @Override
    public List<MeetingInfo> queryMeetingInfoListByUserId(Integer userId) {
        return meetingInfoMapper.queryMeetingInfoListByUserId(userId, Date2Str.getNowDate(new Date()));
    }

    // 通过会议Id获取会议信息
    @Override
    public MeetingInfo getMeetingInfoById(Integer miId) {
        return meetingInfoMapper.getMeetingInfoById(miId, Date2Str.getNowDate(new Date()));
    }

    // 通过会议名称获取会议信息
    @Override
    public MeetingInfo getMeetingInfoByName(String name) {
        return meetingInfoMapper.getMeetingInfoByName(name, Date2Str.getNowDate(new Date()));
    }


}
