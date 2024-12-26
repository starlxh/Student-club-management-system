package com.liu.club_ms.controller;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.jwt.JWUtil;
import com.liu.club_ms.model.Club;
import com.liu.club_ms.model.ClubMember;
import com.liu.club_ms.model.User;
import com.liu.club_ms.service.ClubMemberService;
import com.liu.club_ms.util.Response;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clubMember")
public class ClubMemberController {

    @Resource
    private ClubMemberService clubMemberService;

    // 分页查询或者高级查询社团成员
    @GetMapping("/queryClubMemberList")
    public Response queryClubMemberList(@RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer limit,
                                   String realName, ClubMember clubMember,
                                   HttpServletRequest request) {

        clubMember.setUser(new User(realName));

        /*通过token获取管理员类型和id*/
        String token = request.getHeader("token");
        Integer type = JWUtil.getType(token);
        if(type == 1){
            Integer userId = JWUtil.getUserId(token);
            clubMember.setCaptainId(userId);
        }

        PageInfo<ClubMember> clubMemberPageInfo =
                clubMemberService.queryClubMemberList(page, limit, clubMember);
        long total = clubMemberPageInfo.getTotal();
        Response response = new Response();
        response.setData(clubMemberPageInfo.getList());
        response.setTotal(total);
        response.setCode(20000);
        return response;
    }

    // 通过社长ID查询社团成员
    @GetMapping("/queryClubMemberListByCaptainId")
    public Response queryClubMemberListByCaptainId(HttpServletRequest request) {
        // 通过token获取管理员类型和id
        String token = request.getHeader("token");
        Integer type = JWUtil.getType(token);
        Integer userId = null;
        if(type == 1){
            userId = JWUtil.getUserId(token);
        }

        List<ClubMember> clubMemberList =
                clubMemberService.queryClubMembersByCaptainId(userId);
        long total = clubMemberList.size();

        Response response = new Response();
        response.setData(clubMemberList);
        response.setTotal(total);
        response.setCode(20000);
        return response;
    }

    // 添加社团成员
    @PostMapping("/addClubMember")
    public Response addClubMember(@RequestBody ClubMember clubMember) {
        if(clubMemberService.addClubMember(clubMember)){
            return Response.ok();
        }
        return Response.fail("添加社团成员失败！");
    }

    // 编辑社团成员信息
    @PostMapping("/editClubMember")
    public Response editClubMember(@RequestBody ClubMember clubMember) {
        if(clubMemberService.editClubMember(clubMember)){
            return Response.ok();
        }
        return Response.fail("修改社团成员信息失败！");
    }

    // 通过ID删除社团成员
    @DeleteMapping("/deleteById")
    public Response deleteClubMember(Integer clubMemberId) {
        if(clubMemberService.deleteById(clubMemberId)){
            return Response.ok();
        }
        return Response.fail("删除社团成员失败！");
    }

}
