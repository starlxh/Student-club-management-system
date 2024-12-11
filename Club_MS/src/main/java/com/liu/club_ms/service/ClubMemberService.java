package com.liu.club_ms.service;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.model.ClubMember;

public interface ClubMemberService {
    /*用于分页查询或者高级查询社团成员*/
    PageInfo<ClubMember> queryClubMemberList(Integer page, Integer limit, ClubMember clubMember);

    /*用于添加社团成员*/
    Integer addClubMember(ClubMember clubMember);

    /*用于删除社团成员*/
    Integer deleteClubMemberById(Integer clubMemberId);

    /*用于修改社团成员信息*/
    Integer editClubMember(ClubMember clubMember);
}
