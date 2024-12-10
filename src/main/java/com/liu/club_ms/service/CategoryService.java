package com.liu.club_ms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.liu.club_ms.model.Category;

public interface CategoryService extends IService<Category> {
//  添加社团类型
    int add(Category category);

//  支持分页以及高级查询
    PageInfo<Category> queryCategoryList(int page, int pageSize, Category category);
}
