package com.liu.club_ms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.club_ms.mapper.LeaveInfoMapper;
import com.liu.club_ms.model.LeaveInfo;
import com.liu.club_ms.service.LeaveInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveInfoServiceImpl implements LeaveInfoService {
    @Autowired
    LeaveInfoMapper leaveInfoMapper;


    @Override
    public PageInfo<LeaveInfo> queryLeaveInfoList(int page, int limit, String realName, Integer clubId, Integer captainId) {
        PageHelper.startPage(page, limit);
        List<LeaveInfo> list = leaveInfoMapper.queryLeaveInfoList(realName, clubId, captainId);
        return new PageInfo<>(list);
    }

    @Override
    public boolean editLeaveInfo(LeaveInfo leaveInfo) {
        int num = leaveInfoMapper.editLeaveInfo(leaveInfo);
        return num > 0;
    }

    @Override
    public boolean addLeaveInfo(LeaveInfo leaveInfo) {
        int num = leaveInfoMapper.addLeaveInfo(leaveInfo);
        return num > 0;
    }

    @Override
    public boolean deleteById(Integer leaveInfoId) {
        int num = leaveInfoMapper.deleteById(leaveInfoId);
        return num > 0;
    }
}
