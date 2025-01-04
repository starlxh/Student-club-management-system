package com.liu.club_ms.controller;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.jwt.JWUtil;
import com.liu.club_ms.model.Club;
import com.liu.club_ms.service.ClubService;
import com.liu.club_ms.util.Response;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/club")
public class ClubController {
    @Resource
    private ClubService clubService;

    // 分页查询或高级查询社团信息
    @GetMapping("/queryClubList")
    public Response queryClubList(@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit,
                                  Club club,
                                  HttpServletRequest request) {
        String token = request.getHeader("token");
        if(token != null && JWUtil.getType(token) == 1){
            club.setCaptainId(JWUtil.getUserId(token));
        }
        PageInfo<Club> pageInfo = clubService.queryClubList(page, limit, club);
        long num = pageInfo.getTotal();
        return Response.ok(num, pageInfo.getList());
    }

    // 分页查询或高级查询社团信息
    @GetMapping("/queryClubListPublic")
    public Response queryClubListPublic(@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit,
                                  Club club,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        String token = request.getHeader("token");
        response.addHeader("token", token);
        response.setHeader("Access-Control-Expose-Headers", "token");
        PageInfo<Club> pageInfo = clubService.queryClubList(page, limit, club);
        long num = pageInfo.getTotal();
        return Response.ok(num, pageInfo.getList());
    }

    // 查询所有社团信息
    @GetMapping("/queryAllClubList")
    public Response queryAllClubList(HttpServletRequest request) {
        String token = request.getHeader("token");
        List<Club> list = null;
        if(JWUtil.getType(token) == 1){
            list = clubService.queryAllClubList(JWUtil.getUserId(token));
        }else{
            list = clubService.queryAllClubList(null);
        }

        return Response.ok(list);
    }

    // 添加社团
    @PostMapping("/addClub")
    public Response addClub(@RequestBody Club club) {
        if(clubService.addClub(club)){
            return Response.ok();
        }
        return Response.fail("添加社团失败！");
    }

    // 编辑社团信息
    @PostMapping("/editClub")
    public Response editClub(@RequestBody Club club) {
        if(clubService.editClub(club))
        {
            return Response.ok();
        }
        return Response.fail("编辑社团信息失败！");
    }

    // 通过ID删除社团
    @DeleteMapping("/deleteById")
    public Response deleteClub(Integer clubId) {
        if(clubService.deleteById(clubId))
        {
            return Response.ok();
        }
        return Response.fail("删除社团失败！");
    }

    // 通过用户ID查询已加入的社团
    @GetMapping("/queryJoinedClubListByUserId")
    public Response queryJoinedClubListByUserId(HttpServletRequest request) {
        String token = request.getHeader("token");
        List<Club> list = clubService.queryJoinedClubListByUserId(JWUtil.getUserId(token));
        return Response.ok(list);
    }

    // 通过用户Id查询该用户管理的社团
    @GetMapping("/queryManagedClubListByUserId")
    public Response queryManagedClubListByUserId(HttpServletRequest request) {
        String token = request.getHeader("token");
        List<Club> list = clubService.queryManagedClubListByUserId(JWUtil.getUserId(token));
        return Response.ok(list);
    }

    // 通过社团Id查询社团信息
    @GetMapping("/queryClubInfoById")
    public Response queryClubInfoById(Integer clubId,
                                      String token,
                                      HttpServletResponse response) {
        response.addHeader("token", token);
        response.setHeader("Access-Control-Expose-Headers", "token");
        return Response.ok(clubService.queryClubInfoById(clubId));
    }

    // 通过用户ID和社团ID检查用户是否已经参加该社团
    @GetMapping("/checkIsJoinedById")
    public Response checkIsJoinedById(Integer clubId,
                                      HttpServletRequest request) {
        String token = request.getHeader("token");
        if(clubService.checkIsJoinedById(clubId, JWUtil.getUserId(token))){
            return Response.ok(1);
        }
        return Response.ok(0);
    }
}
