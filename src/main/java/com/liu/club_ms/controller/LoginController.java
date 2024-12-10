package com.liu.club_ms.controller;

import com.liu.club_ms.jwt.JWUtil;
import com.liu.club_ms.model.User;
import com.liu.club_ms.service.UserService;
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
    private UserService userService;

    //用于登录验证
    @PostMapping("/loginIn")
    public Map loginIn(@RequestBody User user){
        User user1 = userService.queryByNamePwdType(user);
        Map map = new HashMap();
        if(user1 != null){
            map.put("token", JWUtil.generateToken(user1));
            map.put("userId", user1.getUserId());
            map.put("userName", user1.getUserName());
            map.put("Type", user1.getType());
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

        Integer type = JWUtil.getType(token);
        List list = new ArrayList();

        if(type == 2){
            list.add("Super");
        }else if(type == 1){
            list.add("Admin");
        }else if(type == 0){
            list.add("User");
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
