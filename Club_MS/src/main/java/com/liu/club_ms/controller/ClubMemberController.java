package com.liu.club_ms.controller;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.jwt.JWUtil;
import com.liu.club_ms.model.Club;
import com.liu.club_ms.model.ClubMember;
import com.liu.club_ms.model.User;
import com.liu.club_ms.service.ClubMemberService;
import com.liu.club_ms.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clubMember")
public class ClubMemberController {

    private ClubMemberService clubMemberService;

    /*用于分页查询或者高级查询社团成员*/
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
            Club club = new Club();
            club.setCaptainId(userId);
            clubMember.setClub(club);
        }

        PageInfo<ClubMember> clubMemberPageInfo =
                clubMemberService.queryClubMemberList(page, limit, clubMember);
        long total = clubMemberPageInfo.getTotal();
        if(total > 0){
            Response response = new Response();
            response.setData(clubMemberPageInfo.getList());
            response.setTotal(total);
            response.setCode(20000);
            return response;
        }

        return Response.fail("查询申请信息失败！");
    }

    /*用于添加社团成员*/
    @GetMapping("/addClubMember")
    public Response addClubMember(@RequestBody ClubMember clubMember) {
        if(clubMemberService.addClubMember(clubMember) > 0){
            return Response.ok();
        }
        return Response.fail("添加社团成员失败！");
    }

    /*用于删除社团成员*/
    @GetMapping("/deleteById")
    public Response deleteClubMember(Integer clubMemberId) {
        if(clubMemberService.deleteClubMemberById(clubMemberId) > 0){
            return Response.ok();
        }
        return Response.fail("删除社团成员失败！");
    }

    /*用于修改社团成员信息*/
    @PostMapping("/editClubMember")
    public Response editClubMember(@RequestBody ClubMember clubMember) {
        if(clubMemberService.editClubMember(clubMember) > 0){
            return Response.ok();
        }
        return Response.fail("修改社团成员信息失败！");
    }

}
