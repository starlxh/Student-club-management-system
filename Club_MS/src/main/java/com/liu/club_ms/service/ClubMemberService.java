package com.liu.club_ms.service;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.model.ClubMember;

import java.util.List;

public interface ClubMemberService {
    // 分页查询或者高级查询社团成员
    PageInfo<ClubMember> queryClubMemberList(Integer page, Integer limit, ClubMember clubMember);

    // 通过社长ID查询社团成员
    List<ClubMember> queryClubMembersByCaptainId(Integer captainId);

    // 添加社团成员
    boolean addClubMember(ClubMember clubMember);

    // 编辑社团成员信息
    boolean editClubMember(ClubMember clubMember);

    // 通过ID删除社团成员
    boolean deleteById(Integer clubMemberId);
}
