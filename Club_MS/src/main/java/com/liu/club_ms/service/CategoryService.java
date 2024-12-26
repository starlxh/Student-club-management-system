package com.liu.club_ms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.liu.club_ms.model.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {

    // 分页查询或高级查询社团类型
    PageInfo<Category> queryCategoryList(int page, int pageSize, Category category);

    // 添加社团类型
    boolean addCategory(Category category);

    // 查询所有社团类型
    List<Category> queryAllCategoryList();
}
