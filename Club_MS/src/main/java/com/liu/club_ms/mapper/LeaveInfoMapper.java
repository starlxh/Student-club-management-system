package com.liu.club_ms.mapper;

import com.liu.club_ms.model.LeaveInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LeaveInfoMapper {
    List<LeaveInfo> queryLeaveInfoList(String realName, Integer clubId, Integer captainId);

    int editLeaveInfo(LeaveInfo leaveInfo);

    int addLeaveInfo(LeaveInfo leaveInfo);

    int deleteById(Integer leaveInfoId);
}
