package com.liu.club_ms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.club_ms.model.Club;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClubMapper extends BaseMapper<Club> {

//  高级查询
    List<Club> queryClubList(Club club);

//  编辑社团
    Integer editClub(Club club);

//   删除社团
    Integer deleteClubById(Integer clubId);
}
