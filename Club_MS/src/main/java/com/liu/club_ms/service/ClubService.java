package com.liu.club_ms.service;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.model.Club;

import java.util.List;

public interface ClubService{

    // 分页查询和高级查询社团信息
    PageInfo<Club> queryClubList(int page, int limit, Club club);

    // 添加社团
    boolean addClub(Club club);

    // 编辑社团信息
    boolean editClub(Club club);

    // 通过ID删除社团
    boolean deleteById(Integer clubId);

    // 查询所有社团信息
    List<Club> queryAllClubList(Integer captainId);

    // 通过用户ID查询已加入的社团
    List<Club> queryJoinedClubListByUserId(Integer userId);

    // 通过用户Id查询该用户管理的社团
    List<Club> queryManagedClubListByUserId(Integer captainId);

    // 通过社团Id查询社团信息
    Club queryClubInfoById(Integer clubId);

    // 通过用户ID和社团ID检查用户是否已经参加该社团
    boolean checkIsJoinedById(Integer clubId, Integer userId);
}
