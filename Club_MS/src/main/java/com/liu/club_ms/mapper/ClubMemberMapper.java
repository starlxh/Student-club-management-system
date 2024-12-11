package com.liu.club_ms.mapper;

import com.liu.club_ms.model.ApplyInfo;
import com.liu.club_ms.model.ClubMember;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClubMemberMapper {

    /*用于分页查询或者高级查询社团成员*/
    List<ClubMember> queryClubMemberListByPage(ClubMember clubMember);

    /*用于添加社团成员*/
    Integer insert(ClubMember clubMember);

    /*用于删除社团成员*/
    @Delete("delete from club_member where club_member_id = #{clubMemberId}")
    Integer delete(Integer clubMemberId);

    /*用于修改社团成员信息*/
    Integer editClubMember(ClubMember clubMember);
}
