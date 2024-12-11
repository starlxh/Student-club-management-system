package com.liu.club_ms.controller;

import com.liu.club_ms.model.User;
import com.liu.club_ms.service.MailService;
import com.liu.club_ms.service.RedisService;
import com.liu.club_ms.service.UserService;
import com.liu.club_ms.util.Response;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/getAdminIdName")
    public Response getIdAdminName(){

        List<User> list =  userService.queryAllIdName();
        if(!list.isEmpty()){
            return Response.ok(list);
        }else return Response.fail("获取管理员ID和姓名失败！");
    }




}
