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

    // 分页查询或者高级查询社团费用记录
    @Override
    public PageInfo<CostList> queryCostListAll(int page, int limit,
                                               String name,
                                               Integer clubId,
                                               Integer captainId,
                                               String order) {
        PageHelper.startPage(page, limit);
        List<CostList> list = costListMapper.queryCostListAll(name, clubId, captainId, order);
        return new PageInfo<>(list);
    }

    // 编辑社团费用记录
    @Override
    public boolean editCostList(CostList costList) {
        return costListMapper.editCostList(costList) > 0;
    }

    // 添加社团费用记录
    @Override
    public boolean addCostList(CostList costList) {
        return costListMapper.addCostList(costList) > 0;
    }

    // 通过ID删除社团费用记录
    @Override
    public boolean deleteById(Integer costListId) {
        return costListMapper.deleteById(costListId) > 0;
    }

}
