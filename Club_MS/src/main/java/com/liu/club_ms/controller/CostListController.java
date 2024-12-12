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

    @GetMapping("/queryCostList")
    public Response queryCostList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            CostList costList,
            HttpServletRequest request
    )
    {
        //通过token获取用户类型和ID
        String token = request.getHeader("token");
        Integer type = JWUtil.getType(token);
        PageInfo<CostList> costListPageInfo;
        if (type == 1) {
            Integer userId = JWUtil.getUserId(token);
            costListPageInfo = costListService.queryCostListAll(page,
                    limit,
                    costList.getName(),
                    costList.getClubId(),
                    userId);
        } else {
            costListPageInfo = costListService.queryCostListAll(page,
                    limit,
                    costList.getName(),
                    costList.getClubId(),
                    null);
        }

        long total = costListPageInfo.getTotal();
        if (total > 0) {
            Response response = new Response();
            response.setData(costListPageInfo.getList());
            response.setTotal(total);
            response.setCode(20000);
            return response;
        }

        return Response.fail("查询社团费用记录失败！");
    }

    @PostMapping("/editCostList")
    public Response editCostList(@RequestBody CostList costList) {
        if (costListService.editCostList(costList)){
            return Response.ok();
        }else {
            return Response.fail("编辑社团费用记录失败");
        }
    }

    @PostMapping("/addCostList")
    public Response addActivity(@RequestBody CostList costList){


        if(costListService.addCostList(costList)){
            return Response.ok();
        }else {
            return Response.fail("添加社团费用记录失败");
        }
    }

    @DeleteMapping("/deleteById")
    public Response deleteById(Integer costListId){
        if(costListService.deleteById(costListId)){
            return Response.ok();
        }else {
            return Response.fail("删除社团费用记录失败");
        }
    }
}
