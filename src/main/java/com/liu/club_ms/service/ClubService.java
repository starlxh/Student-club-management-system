package com.liu.club_ms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.liu.club_ms.model.Club;

public interface ClubService extends IService<Club> {

//  分页高级查询
    PageInfo<Club> queryClubList(int page, int limit, Club club);

//  添加社团
    Integer addClub(Club club);

//  编辑社团
    Integer editClub(Club club);

//  删除社团
    boolean deleteClubById(Integer clubId);
}
