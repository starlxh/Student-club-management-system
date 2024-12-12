package com.liu.club_ms.service;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.model.LeaveInfo;

public interface LeaveInfoService {
    //高级查询
    PageInfo<LeaveInfo> queryLeaveInfoList(int page, int limit, String realName, Integer clubId, Integer captainId);

    boolean editLeaveInfo(LeaveInfo leaveInfo);

    boolean addLeaveInfo(LeaveInfo leaveInfo);

    boolean deleteById(Integer leaveInfoId);
}
