package com.liu.club_ms.controller;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.jwt.JWUtil;
import com.liu.club_ms.model.ApplyInfo;
import com.liu.club_ms.model.ApplyList;
import com.liu.club_ms.model.Club;
import com.liu.club_ms.service.ApplyService;
import com.liu.club_ms.util.Response;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apply")
public class ApplyController {
    @Resource
    ApplyService applyService;

    @GetMapping("/queryApplyInfoList")
    public Response queryApplyInfoListByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            ApplyInfo applyInfo,
            HttpServletRequest request){

        //通过token获取管理员类型和id
        String token = request.getHeader("token");
        Integer type = JWUtil.getAdminType(token);
        if(type == 0){
            Integer adminId = JWUtil.getAdminId(token);
            Club club = new Club();
            club.setCaptainId(adminId);
            applyInfo.setClub(club);
        }

        PageInfo<ApplyInfo> applyInfoPageInfo =
                applyService.queryApplyInfoListByPage(page, limit, applyInfo);
        long total = applyInfoPageInfo.getTotal();
        if(total > 0){
            Response response = new Response();
            response.setData(applyInfoPageInfo.getList());
            response.setTotal(total);
            response.setCode(20000);
            return response;
        }

        return Response.fail("查询申请信息失败！");
    }

    @GetMapping("/queryApplyListByApplyInfoId")
    public Map queryApplyListById(Integer applyInfoId){
        Map map = new HashMap();
        List<ApplyList> applyLists = applyService.queryApplyListByApplyInfoId(applyInfoId);
        map.put("data", applyLists);
        map.put("code", 20000);
        return map;
    }
}
