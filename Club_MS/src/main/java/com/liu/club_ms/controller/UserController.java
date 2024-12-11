package com.liu.club_ms.controller;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.model.User;
import com.liu.club_ms.service.UserService;
import com.liu.club_ms.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /*用于分页查询或者高级查询社团信息*/
    @GetMapping("/queryUserList")
    public Response queryUserList(@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit,
                                  User user) {
        PageInfo<User> pageInfo = userService.queryUserList(page, limit, user);
        long num = pageInfo.getTotal();
        if(num > 0){
            return Response.ok(num, pageInfo.getList());
        }
        return Response.fail("查询用户信息失败！");
    }

    /*用于删除用户*/
    @DeleteMapping("deleteById")
    public Response deleteById(Integer userId) {
        if(userService.removeById(userId)){
            return Response.ok();
        }
        return Response.fail("删除用户失败！");
    }

    /*用于获取所有管理员Id和用户名*/
    @GetMapping("/getAdminIdName")
    public Response getAdminIdName() {
        List<User> list =  userService.queryAllIdName();
        if(!list.isEmpty()){
            return Response.ok(list);
        }else {
            return Response.fail("获取管理员ID和姓名失败！");
        }
    }

    /*用于修改用户信息*/
    @PostMapping("/editUser")
    public Response editUser(@RequestBody User user) {
        if(userService.updateById(user)){
            return Response.ok();
        }
        return Response.fail("修改用户信息失败！");
    }

    /*用于添加用户*/
    @PostMapping("/addUser")
    public Response addUser(@RequestBody User user) {
        if(userService.save(user)){
            return Response.ok();
        }
        return Response.fail("添加用户失败！");
    }
}
