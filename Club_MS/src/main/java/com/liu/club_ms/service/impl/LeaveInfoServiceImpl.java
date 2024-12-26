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

    // 分页查询或者高级查询在线留言
    @Override
    public PageInfo<LeaveInfo> queryLeaveInfoList(int page, int limit,
                                                  String realName,
                                                  Integer clubId,
                                                  Integer captainId,
                                                  String order) {
        PageHelper.startPage(page, limit);
        List<LeaveInfo> list = leaveInfoMapper.queryLeaveInfoList(realName, clubId, captainId, order);
        return new PageInfo<>(list);
    }

    // 编辑在线留言
    @Override
    public boolean editLeaveInfo(LeaveInfo leaveInfo) {
        return leaveInfoMapper.editLeaveInfo(leaveInfo) > 0;
    }

    // 添加在线留言
    @Override
    public boolean addLeaveInfo(LeaveInfo leaveInfo) {
        return leaveInfoMapper.addLeaveInfo(leaveInfo) > 0;
    }

    // 通过ID删除在线留言
    @Override
    public boolean deleteById(Integer leaveInfoId) {
        return leaveInfoMapper.deleteById(leaveInfoId) > 0;
    }

    // 通过用户ID查询在线留言
    @Override
    public List<LeaveInfo> queryLeaveInfoListByUserId(Integer userId) {
        return leaveInfoMapper.queryLeaveInfoListByUserId(userId);
    }

    // 通过社团ID查询在线留言
    @Override
    public List<LeaveInfo> queryLeaveInfoListByClubId(Integer clubId) {
        return leaveInfoMapper.queryLeaveInfoListByClubId(clubId);
    }

}
