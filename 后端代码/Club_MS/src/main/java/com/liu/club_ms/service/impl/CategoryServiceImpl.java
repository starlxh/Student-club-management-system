package com.liu.club_ms.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.club_ms.mapper.CategoryMapper;
import com.liu.club_ms.model.Category;
import com.liu.club_ms.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl
        extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    // 分页查询或高级查询社团类型
    @Override
    public PageInfo<Category> queryCategoryList(int page, int pageSize, Category category) {
        PageHelper.startPage(page, pageSize);
        List<Category> list = categoryMapper.queryCategoryList(category);
        return new PageInfo<>(list);
    }

    // 添加社团类型
    @Override
    public boolean addCategory(Category category) {
        return categoryMapper.insert(category) > 0;
    }

    // 查询所有社团类型
    @Override
    public List<Category> queryAllCategoryList() {
        return categoryMapper.queryAllCategoryList();
    }
}
