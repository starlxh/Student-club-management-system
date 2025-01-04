package com.liu.club_ms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.liu.club_ms.model.ApplyInfo;
import com.liu.club_ms.model.ApplyList;

import java.util.List;

public interface ApplyService {
    // 分页查询或高级查询入团申请详情
    PageInfo<ApplyInfo> queryApplyInfoList(int page, int limit, ApplyInfo applyInfo);

    // 通过入团申请详情ID查询入团审核记录
    List<ApplyList> queryApplyListByApplyInfoId(Integer applyInfoId);

    // 添加入团审核记录
    boolean addApplyList(ApplyList applyList);

    // 添加入团申请详情
    boolean addApplyInfo(ApplyInfo applyInfo);

    // 通过ID编辑入团申请详情状态
    boolean editApplyInfoStatusById(Integer status, Integer applyInfoId);

    // 通过ID删除入团申请详情
    boolean deleteApplyInfoById(Integer applyInfoId);

    // 通过入团申请详情ID删除入团审核
    boolean deleteApplyListByApplyInfoId(Integer applyInfoId);

    // 通过用户ID查询入团申请记录
    List<ApplyInfo> getApplyInfoListByUserId(Integer userId);

    // 通过用户ID和社团ID查看是否存在入团申请
    boolean checkIsExitByUserIdAndClubId(Integer userId, Integer clubId);
}
