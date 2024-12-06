package com.liu.club_ms.controller;

import com.liu.club_ms.jwt.JWUtil;
import com.liu.club_ms.model.Admin;
import com.liu.club_ms.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private AdminService adminService;

    //登录验证
    @PostMapping("/loginIn")
    public Map loginIn(@RequestBody Admin admin){
        Admin admin1 = adminService.queryByNamePassType(admin);
        Map map = new HashMap();
        if(admin1 != null){
            map.put("token", JWUtil.generateToken(admin1));
            map.put("adminId", admin1.getAdminId());
            map.put("adminName", admin1.getAdminName());
            map.put("adminType", admin1.getAdminType());
            map.put("code", 20000);
            return map;
        }else {
            map.put("code", 300);
            map.put("message", "用户名或者密码错误!");
            return map;
        }
    }

    //通过token获取管理员对象和角色信息
    @GetMapping("/queryAdminInfo")
    public Map queryAdminInfo(String token){
        Map map = new HashMap();

        Integer type = JWUtil.getAdminType(token);
        List list = new ArrayList();
        if(type == 1){
            list.add("Super");
        }else{
            list.add("Admin");
        }

        map.put("code", 20000);
        Map data = new HashMap();
        data.put("roles", list);
        map.put("data", data);
        return map;
    }

    //退出
    @PostMapping("/loginOut")
    public Map logout(){
        Map map = new HashMap();
        map.put("code", 20000);
        map.put("data", null);
        return map;
    }
}
