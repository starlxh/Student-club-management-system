package com.liu.club_ms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.club_ms.mapper.*;
import com.liu.club_ms.model.ApplyInfo;
import com.liu.club_ms.model.Club;
import com.liu.club_ms.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubServiceImpl implements ClubService{

    @Autowired
    private ClubMapper clubMapper;

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private ApplyMapper applyMapper;

    @Autowired
    private ClubMemberMapper clubMemberMapper;

    @Autowired
    private CostListMapper costListMapper;

    @Autowired
    private LeaveInfoMapper leaveInfoMapper;

    @Autowired
    private MeetingInfoMapper meetingInfoMapper;

    // 分页查询和高级查询社团信息
    @Override
    public PageInfo<Club> queryClubList(int page, int limit, Club club) {
        PageHelper.startPage(page, limit);
        List<Club> list = clubMapper.queryClubList(club);
        return new PageInfo<>(list);
    }

    // 添加社团
    @Override
    public boolean addClub(Club club) {
        return clubMapper.addClub(club) > 0;
    }

    // 编辑社团信息
    @Override
    public boolean editClub(Club club) {
        return clubMapper.editClub(club) > 0;
    }

    // 通过ID删除社团
    @Override
    public boolean deleteById(Integer clubId) {

        List<ApplyInfo> applyInfoList = applyMapper.queryApplyInfoIdByClubId(clubId);
        if(!applyInfoList.isEmpty()){
            for (ApplyInfo applyInfo : applyInfoList) {
                applyMapper.deleteApplyListByApplyInfoId(applyInfo.getApplyInfoId());
            }
        }

        return noticeMapper.deleteByClubId(clubId) > 0 &&
                activityMapper.deleteActivityByClubId(clubId) > 0 &&
                costListMapper.deleteCostListWhenDeleteClub(clubId) > 0 &&
                leaveInfoMapper.deleteLeaveInfoWhenDeleteClub(clubId) > 0 &&
                meetingInfoMapper.deleteMeetingInfoByClubId(clubId) > 0 &&
                clubMemberMapper.deleteClubMemberWhenDeleteClub(clubId) > 0 &&
                clubMapper.deleteById(clubId) > 0;
    }

    // 查询所有社团信息
    @Override
    public List<Club> queryAllClubList(Integer captainId) {
        return clubMapper.queryClubAllList(captainId);
    }

    // 通过用户ID查询已加入的社团
    @Override
    public List<Club> queryJoinedClubListByUserId(Integer userId) {
        return clubMapper.queryJoinedClubListByUserId(userId);
    }

    // 通过用户Id查询该用户管理的社团
    @Override
    public List<Club> queryManagedClubListByUserId(Integer captainId) {
        return clubMapper.queryManagedClubListByUserId(captainId);
    }

    // 通过社团Id查询社团信息
    @Override
    public Club queryClubInfoById(Integer clubId) {
        Integer count =
                clubMemberMapper.queryClubMemberCountByClubId(clubId).size();
        Club club = clubMapper.queryClubInfoById(clubId);
        club.setCount(count);
        return club;
    }

    // 通过用户ID和社团ID检查用户是否已经参加该社团
    @Override
    public boolean checkIsJoinedById(Integer clubId, Integer userId) {
        return !(clubMapper.checkIsJoinedById(clubId, userId).isEmpty());
    }
}
