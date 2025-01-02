package com.liu.club_ms.mapper;

import com.liu.club_ms.model.ApplyInfo;
import com.liu.club_ms.model.ClubMember;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClubMemberMapper {

    // 分页查询或者高级查询社团成员
    List<ClubMember> queryClubMemberList(ClubMember clubMember);

    // 通过社长ID查询社团成员
    List<ClubMember> queryClubMembersByCaptainId(Integer captainId);

    // 添加社团成员
    Integer addClubMember(ClubMember clubMember);

    // 编辑社团成员信息
    Integer editClubMember(ClubMember clubMember);

    // 通过ID删除社团成员
    Integer deleteById(Integer clubMemberId);

    // 通过用户ID删除社团成员
    Integer deleteClubMemberByUserId(Integer userId);

    // 通过删除的社团ID删除所有对应社团成员
    Integer deleteClubMemberWhenDeleteClub(Integer clubId);

    // 通过社团Id查询社团人数
    List<ClubMember> queryClubMemberCountByClubId(Integer clubId);

    // 退出社团
    Integer quit(Integer clubId, Integer userId);
}
