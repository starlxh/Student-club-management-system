package com.liu.club_ms.controller;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.jwt.JWUtil;
import com.liu.club_ms.model.LeaveInfo;
import com.liu.club_ms.service.LeaveInfoService;
import com.liu.club_ms.util.Response;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaveInfo")
public class LeaveInfoController {
    @Resource
    private LeaveInfoService leaveInfoService;

    // 分页查询或者高级查询在线留言
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
                    userId,
                    leaveInfo.getOrder());
        } else {
            leaveInfoPageInfo = leaveInfoService.queryLeaveInfoList(page,
                    limit,
                    leaveInfo.getRealName(),
                    leaveInfo.getClubId(),
                    null,
                    leaveInfo.getOrder());
        }

        long total = leaveInfoPageInfo.getTotal();

        Response response = new Response();
        response.setData(leaveInfoPageInfo.getList());
        response.setTotal(total);
        response.setCode(20000);
        return response;
    }

    // 编辑在线留言
    @PostMapping("/editLeaveInfo")
    public Response editLeaveInfo(@RequestBody LeaveInfo leaveInfo) {
        if (leaveInfoService.editLeaveInfo(leaveInfo)){
            return Response.ok();
        }else {
            return Response.fail("编辑在线留言失败");
        }
    }

    // 添加在线留言
    @PostMapping("/addLeaveInfo")
    public Response addLeaveInfo(@RequestBody LeaveInfo leaveInfo,
                                 HttpServletRequest request){
        String token = request.getHeader("token");
        leaveInfo.setUserId(JWUtil.getUserId(token));

        if(leaveInfoService.addLeaveInfo(leaveInfo)){
            return Response.ok();
        }else {
            return Response.fail("添加在线留言失败");
        }
    }

    // 通过ID删除在线留言
    @DeleteMapping("/deleteById")
    public Response deleteById(Integer leaveInfoId){
        if(leaveInfoService.deleteById(leaveInfoId)){
            return Response.ok();
        }else {
            return Response.fail("删除在线留言失败");
        }
    }

    // 通过用户ID查询在线留言
    @GetMapping("/queryLeaveInfoListByUserId")
    public Response queryLeaveInfoListByUserId(HttpServletRequest request){
        String token = request.getHeader("token");
        List<LeaveInfo> list = leaveInfoService.queryLeaveInfoListByUserId(JWUtil.getUserId(token));
        return Response.ok(list);
    }

    // 通过社团ID查询在线留言
    @GetMapping("/queryLeaveInfoListByClubId")
    public Response queryLeaveInfoListByClubId(Integer clubId,
                                               String token,
                                               HttpServletResponse response){
        response.addHeader("token", token);
        response.setHeader("Access-Control-Expose-Headers", "token");
        List<LeaveInfo> list = leaveInfoService.queryLeaveInfoListByClubId(clubId);
        return Response.ok(list);
    }

}
