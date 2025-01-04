package com.liu.club_ms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.club_ms.model.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    // 分页查询或高级查询社团类型
    List<Category> queryCategoryList(Category category);

    // 查询所有社团类型
    List<Category> queryAllCategoryList();
}
