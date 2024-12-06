package com.liu.club_ms.controller;

import com.liu.club_ms.model.Admin;
import com.liu.club_ms.service.AdminService;
import com.liu.club_ms.util.Response;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @GetMapping("/getIdName")
    public Response getIdName(){
        List<Admin> list =  adminService.queryAllIdName();
        if(!list.isEmpty()){
            return Response.ok(list);
        }else return Response.fail("获取管理员ID和姓名失败！");
    }


}
