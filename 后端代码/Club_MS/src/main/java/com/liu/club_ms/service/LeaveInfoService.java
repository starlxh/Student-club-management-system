package com.liu.club_ms.service;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.model.LeaveInfo;

import java.util.List;

public interface LeaveInfoService {
    // 分页查询或者高级查询在线留言
    PageInfo<LeaveInfo> queryLeaveInfoList(int page, int limit,
                                           String realName,
                                           Integer clubId,
                                           Integer captainId,
                                           String order);

    // 编辑在线留言
    boolean editLeaveInfo(LeaveInfo leaveInfo);

    // 添加在线留言
    boolean addLeaveInfo(LeaveInfo leaveInfo);

    // 通过ID删除在线留言
    boolean deleteById(Integer leaveInfoId);

    // 通过用户ID查询在线留言
    List<LeaveInfo> queryLeaveInfoListByUserId(Integer userId);

    // 通过社团ID查询在线留言
    List<LeaveInfo> queryLeaveInfoListByClubId(Integer clubId);
}
