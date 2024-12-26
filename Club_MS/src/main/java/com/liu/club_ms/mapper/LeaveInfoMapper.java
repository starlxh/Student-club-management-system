package com.liu.club_ms.mapper;

import com.liu.club_ms.model.LeaveInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LeaveInfoMapper {
    // 分页查询或者高级查询在线留言
    List<LeaveInfo> queryLeaveInfoList(String realName,
                                       Integer clubId,
                                       Integer captainId,
                                       String order);

    // 编辑在线留言
    Integer editLeaveInfo(LeaveInfo leaveInfo);

    // 添加在线留言
    Integer addLeaveInfo(LeaveInfo leaveInfo);

    // 通过ID删除在线留言
    Integer deleteById(Integer leaveInfoId);

    // 通过用户ID删除在线留言
    Integer deleteByUserId(Integer userId);

    // 通过删除的社团ID删除所有对应在线留言
    Integer deleteLeaveInfoWhenDeleteClub(Integer clubId);

    // 通过用户ID查询在线留言
    List<LeaveInfo> queryLeaveInfoListByUserId(Integer userId);

    // 通过社团ID查询在线留言
    List<LeaveInfo> queryLeaveInfoListByClubId(Integer clubId);
}
