package com.liu.club_ms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.club_ms.mapper.*;
import com.liu.club_ms.model.ApplyInfo;
import com.liu.club_ms.model.Club;
import com.liu.club_ms.model.ClubMember;
import com.liu.club_ms.service.ClubService;
import com.liu.club_ms.util.Date2Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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
        ClubMember clubMember = new ClubMember();
        clubMember.setClubId(club.getClubId());
        clubMember.setUserId(club.getCaptainId());
        clubMember.setJoinTime(Date2Str.getNowDate(new Date()));
        clubMemberMapper.addClubMember(clubMember);
        return clubMapper.addClub(club) > 0;
    }

    // 编辑社团信息
    @Override
    public boolean editClub(Club club) {
        Club club1 = clubMapper.queryClubInfoById(club.getClubId());
        if(club1 != null && club.getCaptainId() != null){
            if(!Objects.equals(club1.getCaptainId(), club.getCaptainId())){
                activityMapper.updateUserIdByClubId(club.getClubId(), club.getCaptainId());
                meetingInfoMapper.updateMeetingInfoUserIdByClubId(club.getClubId(), club.getCaptainId());
                ClubMember clubMember = new ClubMember();
                clubMember.setClubId(club.getClubId());
                clubMember.setUserId(club.getCaptainId());
                clubMember.setJoinTime(Date2Str.getNowDate(new Date()));
                clubMemberMapper.addClubMember(clubMember);
                clubMemberMapper.deleteClubMemberByUserId(club1.getCaptainId());
            }
        }

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
        noticeMapper.deleteByClubId(clubId);
        activityMapper.deleteActivityByClubId(clubId);
        costListMapper.deleteCostListWhenDeleteClub(clubId);
        leaveInfoMapper.deleteLeaveInfoWhenDeleteClub(clubId);
        meetingInfoMapper.deleteMeetingInfoByClubId(clubId);
        clubMemberMapper.deleteClubMemberWhenDeleteClub(clubId);
        return  clubMapper.deleteById(clubId) > 0;
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
