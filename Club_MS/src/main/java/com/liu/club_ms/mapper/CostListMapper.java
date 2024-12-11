package com.liu.club_ms.mapper;

import com.liu.club_ms.model.CostList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CostListMapper {
    //高级查询
    List<CostList> queryCostListAll(String name, Integer clubId, Integer captainId);

    int editCostList(CostList costList);

    int addCostList(CostList costList);

    int deleteById(Integer costListId);
}
