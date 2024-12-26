package com.liu.club_ms.mapper;

import com.liu.club_ms.model.ApplyInfo;
import com.liu.club_ms.model.ApplyList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApplyMapper {
    //  分页查询或高级查询入团申请详情
    List<ApplyInfo> queryApplyInfoList(ApplyInfo applyInfo);

    //  通过入团申请详情ID查询入团审核记录
    List<ApplyList> queryApplyListByApplyInfoId(Integer applyInfoId);

    // 添加入团审核记录
    Integer addApplyList(ApplyList applyList);

    // 添加入团申请详情
    Integer addApplyInfo(ApplyInfo applyInfo);

    // 通过ID编辑入团申请详情状态
    Integer editApplyInfoStatusById(Integer status, Integer applyInfoId);

    // 通过入团申请详情ID删除入团审核
    Integer deleteApplyListByApplyInfoId(Integer applyInfoId);

    // 通过ID删除入团申请详情
    Integer deleteApplyInfoById(Integer applyInfoId);

    // 通过用户ID查询入团申请详情ID
    List<ApplyInfo> queryApplyInfoIdByUserId(Integer userId);

    // 通过用户ID删除入团申请详情
    Integer deleteApplyInfoByUserId(Integer userId);

    // 通过社团ID查询入团申请详情ID
    List<ApplyInfo> queryApplyInfoIdByClubId(Integer clubId);

    // 通过社团ID删除入团申请详情
    Integer deleteApplyInfoByClubId(Integer clubId);

    // 更新入团审核当审核人被删除
    Integer updateApplyInfoWhenDeleteAuthor(Integer authorId);

    // 通过用户ID查询入团申请记录
    List<ApplyInfo> getApplyInfoListByUserId(Integer userId);

    // 通过用户ID和社团ID查看是否存在入团申请
    List<ApplyInfo> checkIsExitByUserIdAndClubId(Integer userId, Integer clubId);
}
