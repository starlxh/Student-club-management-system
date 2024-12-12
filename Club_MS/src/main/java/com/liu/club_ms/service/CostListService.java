package com.liu.club_ms.service;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.model.CostList;

public interface CostListService {
    PageInfo<CostList> queryCostListAll(int page, int limit, String name, Integer clubId, Integer captainId);

    boolean editCostList(CostList costList);

    boolean addCostList(CostList costList);

    boolean deleteById(Integer costListId);
}
