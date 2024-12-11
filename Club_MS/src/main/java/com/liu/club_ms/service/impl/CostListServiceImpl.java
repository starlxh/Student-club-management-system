package com.liu.club_ms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.club_ms.mapper.CostListMapper;
import com.liu.club_ms.model.CostList;
import com.liu.club_ms.service.CostListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CostListServiceImpl implements CostListService {
    @Autowired
    CostListMapper costListMapper;

    @Override
    public PageInfo<CostList> queryCostListAll(int page, int limit,
                                               String name,
                                               Integer clubId,
                                               Integer captainId) {
        PageHelper.startPage(page, limit);
        List<CostList> list = costListMapper.queryCostListAll(name, clubId, captainId);
        return new PageInfo<>(list);
    }

    @Override
    public boolean editCostList(CostList costList) {
        int num = costListMapper.editCostList(costList);
        return num > 0;
    }

    @Override
    public boolean addCostList(CostList costList) {
        int num = costListMapper.addCostList(costList);
        return num > 0;
    }

    @Override
    public boolean deleteById(Integer costListId) {
        int num = costListMapper.deleteById(costListId);
        return num > 0;
    }

}
