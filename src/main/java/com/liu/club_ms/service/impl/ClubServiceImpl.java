package com.liu.club_ms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.club_ms.mapper.ClubMapper;
import com.liu.club_ms.model.Club;
import com.liu.club_ms.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubServiceImpl
        extends ServiceImpl<ClubMapper, Club>
        implements ClubService {

    @Autowired
    private ClubMapper clubMapper;

//  高级查询和分页
    @Override
    public PageInfo<Club> queryClubList(int page, int limit, Club club) {
        PageHelper.startPage(page, limit);
        List<Club> list = clubMapper.queryClubList(club);
        return new PageInfo<>(list);
    }

//  添加社团
    @Override
    public Integer addClub(Club club) {
        return clubMapper.insert(club);
    }

//  编辑社团
    @Override
    public Integer editClub(Club club) {
        return clubMapper.editClub(club);
    }

//  删除社团
    @Override
    public boolean deleteClubById(Integer clubId) {
        return clubMapper.deleteClubById(clubId) > 0;
    }
}
