package com.liu.club_ms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.club_ms.mapper.ClubMemberMapper;
import com.liu.club_ms.model.ClubMember;
import com.liu.club_ms.service.ClubMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubMemberServiceImpl implements ClubMemberService {

    @Autowired
    private ClubMemberMapper clubMemberMapper;

    // 分页查询或者高级查询社团成员
    @Override
    public PageInfo<ClubMember> queryClubMemberList(Integer page, Integer limit,ClubMember clubMember) {
        PageHelper.startPage(page, limit);
        List<ClubMember> list = clubMemberMapper.queryClubMemberList(clubMember);
        return new PageInfo<>(list);
    }

    // 通过社长ID查询社团成员
    @Override
    public List<ClubMember> queryClubMembersByCaptainId(Integer captainId) {
        return clubMemberMapper.queryClubMembersByCaptainId(captainId);
    }

    // 添加社团成员
    @Override
    public boolean addClubMember(ClubMember clubMember) {

        return clubMemberMapper.addClubMember(clubMember) > 0;
    }

    // 编辑社团成员信息
    @Override
    public boolean editClubMember(ClubMember clubMember) {
        return clubMemberMapper.editClubMember(clubMember) > 0;
    }

    // 通过ID删除社团成员
    @Override
    public boolean deleteById(Integer clubMemberId) {
        return clubMemberMapper.deleteById(clubMemberId) > 0;
    }

}
