package com.liu.club_ms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.club_ms.model.Club;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClubMapper{

    // 分页查询和高级查询社团信息
    List<Club> queryClubList(Club club);

    // 添加社团
    Integer addClub(Club club);

    // 编辑社团信息
    Integer editClub(Club club);

    // 通过ID删除社团
    Integer deleteById(Integer clubId);

    // 通过删除的社长ID更新社团信息
    Integer updateClubWhenDeleteCaptain(Integer captainId);

    // 通过删除的社团类型ID更新社团信息
    Integer updateClubWhenDeleteCategory(Integer categoryId);

    // 通过删除的创建者ID更新社团信息
    Integer updateClubWhenDeleteCreateBy(Integer createBy);

    // 查询所有社团信息
    List<Club> queryClubAllList(Integer captainId);

    // 通过用户ID查询已加入的社团
    List<Club> queryJoinedClubListByUserId(Integer userId);

    // 通过用户Id查询该用户管理的社团
    List<Club> queryManagedClubListByUserId(Integer captainId);

    // 通过社团Id查询社团信息
    Club queryClubInfoById(Integer clubId);

    // 通过用户ID和社团ID检查用户是否已经参加该社团
    List<Club> checkIsJoinedById(Integer clubId, Integer userId);
}
