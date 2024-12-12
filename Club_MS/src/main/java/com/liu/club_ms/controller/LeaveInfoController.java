package com.liu.club_ms.controller;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.jwt.JWUtil;
import com.liu.club_ms.model.CostList;
import com.liu.club_ms.model.LeaveInfo;
import com.liu.club_ms.service.LeaveInfoService;
import com.liu.club_ms.util.Response;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leaveInfo")
public class LeaveInfoController {
    @Resource
    private LeaveInfoService leaveInfoService;

    @GetMapping("/queryLeaveInfoList")
    public Response queryLeaveInfoList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            LeaveInfo leaveInfo,
            HttpServletRequest request) {
        //通过token获取用户类型和ID
        String token = request.getHeader("token");
        Integer type = JWUtil.getType(token);
        PageInfo<LeaveInfo> leaveInfoPageInfo;
        if (type == 1) {
            Integer userId = JWUtil.getUserId(token);
            leaveInfoPageInfo = leaveInfoService.queryLeaveInfoList(page,
                    limit,
                    leaveInfo.getRealName(),
                    leaveInfo.getClubId(),
                    userId);
        } else {
            leaveInfoPageInfo = leaveInfoService.queryLeaveInfoList(page,
                    limit,
                    leaveInfo.getRealName(),
                    leaveInfo.getClubId(),
                    null);
        }

        long total = leaveInfoPageInfo.getTotal();
        if (total > 0) {
            Response response = new Response();
            response.setData(leaveInfoPageInfo.getList());
            response.setTotal(total);
            response.setCode(20000);
            return response;
        }

        return Response.fail("查询留言信息失败！");
    }

    @PostMapping("/editLeaveInfo")
    public Response editCostList(@RequestBody LeaveInfo leaveInfo) {
        if (leaveInfoService.editLeaveInfo(leaveInfo)){
            return Response.ok();
        }else {
            return Response.fail("编辑社团费用记录失败");
        }
    }

    @PostMapping("/addLeaveInfo")
    public Response addLeaveInfo(@RequestBody LeaveInfo leaveInfo){


        if(leaveInfoService.addLeaveInfo(leaveInfo)){
            return Response.ok();
        }else {
            return Response.fail("添加社团费用记录失败");
        }
    }

    @DeleteMapping("/deleteById")
    public Response deleteById(Integer leaveInfoId){
        if(leaveInfoService.deleteById(leaveInfoId)){
            return Response.ok();
        }else {
            return Response.fail("删除社团费用记录失败");
        }
    }

}
