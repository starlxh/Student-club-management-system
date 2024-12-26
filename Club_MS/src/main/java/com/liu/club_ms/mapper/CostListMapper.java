package com.liu.club_ms.mapper;

import com.liu.club_ms.model.CostList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CostListMapper {

    // 分页查询或者高级查询社团费用记录
    List<CostList> queryCostListAll(String name, Integer clubId, Integer captainId, String order);

    // 编辑社团费用记录
    Integer editCostList(CostList costList);

    // 添加社团费用记录
    Integer addCostList(CostList costList);

    // 通过ID删除社团费用记录
    Integer deleteById(Integer costListId);

    // 通过删除的社团ID删除所有对应社团费用记录
    Integer deleteCostListWhenDeleteClub(Integer clubId);
}
