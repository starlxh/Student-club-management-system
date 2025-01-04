package com.liu.club_ms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.club_ms.mapper.ApplyMapper;
import com.liu.club_ms.mapper.ClubMemberMapper;
import com.liu.club_ms.model.ApplyInfo;
import com.liu.club_ms.model.ApplyList;
import com.liu.club_ms.model.ClubMember;
import com.liu.club_ms.service.ApplyService;
import com.liu.club_ms.util.Date2Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ApplyServiceImpl
        implements ApplyService {
    @Autowired
    ApplyMapper applyMapper;

    @Autowired
    ClubMemberMapper clubMemberMapper;

    // 分页查询或高级查询入团申请详情
    @Override
    public PageInfo<ApplyInfo> queryApplyInfoList(int page, int limit, ApplyInfo applyInfo) {
        PageHelper.startPage(page, limit);
        List<ApplyInfo> applyInfoList = applyMapper.queryApplyInfoList(applyInfo);
        return new PageInfo<>(applyInfoList);
    }

    // 通过入团申请详情ID查询入团审核记录
    @Override
    public List<ApplyList> queryApplyListByApplyInfoId(Integer applyInfoId) {
        return applyMapper.queryApplyListByApplyInfoId(applyInfoId);
    }

    // 添加入团审核记录
    @Override
    public boolean addApplyList(ApplyList applyList) {
        return applyMapper.addApplyList(applyList) > 0;
    }

    // 添加入团申请详情
    @Override
    public boolean addApplyInfo(ApplyInfo applyInfo) {
        return applyMapper.addApplyInfo(applyInfo) > 0;
    }

    // 通过ID编辑入团申请详情状态
    @Override
    public boolean editApplyInfoStatusById(Integer status, Integer applyInfoId) {
        if(status == 1){
            ClubMember clubMember = new ClubMember();
            clubMember.setClubId(applyMapper.getClubIdByApplyInfoId(applyInfoId));
            clubMember.setUserId(applyMapper.getUserIdByApplyInfoId(applyInfoId));
            clubMember.setJoinTime(Date2Str.getNowDate(new Date()));
            clubMemberMapper.addClubMember(clubMember);
        }
        return applyMapper.editApplyInfoStatusById(status, applyInfoId) > 0;
    }

    // 通过ID删除入团申请详情
    @Override
    public boolean deleteApplyInfoById(Integer applyInfoId) {
        return applyMapper.deleteApplyInfoById(applyInfoId) > 0;
    }

    // 通过入团申请详情ID删除入团审核
    @Override
    public boolean deleteApplyListByApplyInfoId(Integer applyInfoId) {
        return applyMapper.deleteApplyListByApplyInfoId(applyInfoId) > 0;
    }

    // 通过用户ID查询入团申请记录
    @Override
    public List<ApplyInfo> getApplyInfoListByUserId(Integer userId) {
        return applyMapper.getApplyInfoListByUserId(userId);
    }

    // 通过用户ID和社团ID查看是否存在入团申请
    @Override
    public boolean checkIsExitByUserIdAndClubId(Integer userId, Integer clubId) {
        return !(applyMapper.checkIsExitByUserIdAndClubId(userId, clubId).isEmpty());
    }


}
