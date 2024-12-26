package com.liu.club_ms.controller;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.jwt.JWUtil;
import com.liu.club_ms.model.*;
import com.liu.club_ms.service.CostListService;
import com.liu.club_ms.util.Response;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/costList")
public class CostListController {
    @Resource
    private CostListService costListService;

    // 分页查询或者高级查询社团费用记录
    @GetMapping("/queryCostListAll")
    public Response queryCostList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            CostList costList,
            HttpServletRequest request
    )
    {
        //通过token获取用户ID
        String token = request.getHeader("token");
        PageInfo<CostList> costListPageInfo;
        costListPageInfo = costListService.queryCostListAll(page,
                limit,
                costList.getName(),
                costList.getClubId(),
                JWUtil.getUserId(token),
                costList.getOrder());

        long total = costListPageInfo.getTotal();
        Response response = new Response();
        response.setData(costListPageInfo.getList());
        response.setTotal(total);
        response.setCode(20000);
        return response;
    }

    // 编辑社团费用记录
    @PostMapping("/editCostList")
    public Response editCostList(@RequestBody CostList costList) {
        if (costListService.editCostList(costList)){
            return Response.ok();
        }else {
            return Response.fail("编辑社团费用记录失败");
        }
    }

    // 添加社团费用记录
    @PostMapping("/addCostList")
    public Response addCostList(@RequestBody CostList costList){


        if(costListService.addCostList(costList)){
            return Response.ok();
        }else {
            return Response.fail("添加社团费用记录失败");
        }
    }

    // 通过ID删除社团费用记录
    @DeleteMapping("/deleteById")
    public Response deleteById(Integer costListId){
        if(costListService.deleteById(costListId)){
            return Response.ok();
        }else {
            return Response.fail("删除社团费用记录失败");
        }
    }
}
