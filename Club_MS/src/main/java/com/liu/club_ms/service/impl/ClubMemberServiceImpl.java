package com.liu.club_ms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.club_ms.mapper.ClubMemberMapper;
import com.liu.club_ms.model.ClubMember;
import com.liu.club_ms.service.ClubMemberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubMemberServiceImpl implements ClubMemberService {

    private ClubMemberMapper clubMemberMapper;

    /*用于分页查询或者高级查询社团成员*/
    @Override
    public PageInfo<ClubMember> queryClubMemberList(Integer page, Integer limit,ClubMember clubMember) {
        PageHelper.startPage(page, limit);
        List<ClubMember> list = clubMemberMapper.queryClubMemberListByPage(clubMember);
        return new PageInfo<>(list);
    }

    /*用于添加社团成员*/
    @Override
    public Integer addClubMember(ClubMember clubMember) {
        return clubMemberMapper.insert(clubMember);
    }

    /*用于删除社团成员*/
    @Override
    public Integer deleteClubMemberById(Integer clubMemberId) {
        return clubMemberMapper.delete(clubMemberId) > 0 ? 1 : 0;
    }

    /*用于修改社团成员信息*/
    @Override
    public Integer editClubMember(ClubMember clubMember) {
        return clubMemberMapper.editClubMember(clubMember);
    }
}
