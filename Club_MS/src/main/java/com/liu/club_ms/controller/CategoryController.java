package com.liu.club_ms.controller;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.model.Category;
import com.liu.club_ms.service.CategoryService;
import com.liu.club_ms.util.Response;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

//  添加社团类型
    @PostMapping("/addCategory")
    public Response addCategory(@RequestBody Category category) {
        if (categoryService.add(category) > 0){
            return Response.ok();
        }
        return Response.fail("添加社团类型失败！");
    }

//  编辑社团类型
    @PostMapping("/editCategory")
    public Response editCategory(@RequestBody Category category) {
        if (categoryService.updateById(category)){
            return Response.ok();
        }
        return Response.fail("编辑社团类型失败！");
    }

//  删除社团类型
    @DeleteMapping("/deleteById")
    public Response deleteById(Integer categoryId) {
        if (categoryService.removeById(new Category(categoryId, null, null,null))){
            return Response.ok();
        }
        return Response.fail("删除社团类型失败！");
    }

//  分页高级查询
    @GetMapping("/queryCategoryList")
    public Response queryCategoryList(@RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer limit,
                                    String categoryName, String order){
        PageInfo<Category> pageInfo = categoryService.queryCategoryList(page, limit, new Category(null, categoryName, null, order));
        long num = pageInfo.getTotal();
        if (num > 0){
            return Response.ok(num, pageInfo.getList());
        }
        return Response.fail("查询社团类型失败！");
    }
}
