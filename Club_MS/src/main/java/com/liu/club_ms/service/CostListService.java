package com.liu.club_ms.service;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.model.CostList;

public interface CostListService {

    // 分页查询或者高级查询社团费用记录
    PageInfo<CostList> queryCostListAll(int page, int limit,
                                        String name,
                                        Integer clubId,
                                        Integer captainId,
                                        String oreder);

    // 编辑社团费用记录
    boolean editCostList(CostList costList);

    // 添加社团费用记录
    boolean addCostList(CostList costList);

    // 通过ID删除社团费用记录
    boolean deleteById(Integer costListId);
}
