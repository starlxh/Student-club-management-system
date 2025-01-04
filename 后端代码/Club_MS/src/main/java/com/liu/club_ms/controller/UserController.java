package com.liu.club_ms.controller;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.jwt.JWUtil;
import com.liu.club_ms.model.AdminStatus;
import com.liu.club_ms.model.User;
import com.liu.club_ms.service.RedisService;
import com.liu.club_ms.service.UserService;
import com.liu.club_ms.util.Response;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Resource
    private RedisService redisService;

    /* 用户管理相关 */
    // 用于分页查询或者高级查询用户信息
    @GetMapping("/queryUserList")
    public Response queryUserList(@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit,
                                  User user) {
        PageInfo<User> pageInfo = userService.queryUserList(page,
                limit,
                user.getRealName(),
                user.getType(),
                user.getOrder());
        long num = pageInfo.getTotal();
        return Response.ok(num, pageInfo.getList());
    }

    // 用于通过ID删除用户
    @DeleteMapping("deleteById")
    public Response deleteById(Integer userId,
                               HttpServletRequest request) {
        String token = request.getHeader("token");
        if(userService.deleteById(userId, JWUtil.getType(token))){
            return Response.ok();
        }
        return Response.fail("删除用户失败！");
    }

    // 用于编辑用户信息
    @PostMapping("/editUser")
    public Response editUser(@RequestBody User user, String captcha, HttpServletRequest request) {
        if(user.getUserId() == null){
            String token = request.getHeader("token");
            user.setUserId(JWUtil.getUserId(token));
        }

        if(captcha != null){
            String storedCaptcha = redisService.getCaptcha(userService.getEmailByUserId(user.getUserId()));
            System.out.println("验证码：" + storedCaptcha);
            if (storedCaptcha != null && storedCaptcha.equals(captcha)) {
                redisService.deleteCaptcha(user.getEmail()); // 验证成功后删除验证码
            } else {
                return Response.fail("验证码错误或已过期！");
            }
        }

        if(userService.editUser(user)){
            return Response.ok();
        }
        return Response.fail("修改用户信息失败！");
    }

    // 用于添加用户
    @PostMapping("/addUser")
    public Response addUser(@RequestBody User user) {
        if(userService.addUser(user)){
            return Response.ok();
        }
        return Response.fail("添加用户失败！");
    }

    // 通过用户ID获取用户信息
    @GetMapping("/getUserInfoById")
    public Response getUserInfoById(HttpServletRequest request) {
        String token = request.getHeader("token");
        User user = userService.getUserInfoById(JWUtil.getUserId(token));
        if(user != null){
            return Response.ok(user);
        }
        return Response.fail("获取用户信息失败！");
    }

    // 注销账号
    @DeleteMapping("/breakAccount")
    public Response breakAccount(HttpServletRequest request) {
        String token = request.getHeader("token");
        if(userService.deleteById(JWUtil.getUserId(token), JWUtil.getType(token))){
            return Response.ok();
        }
        return Response.fail("注销账号失败！");
    }

/* 管理员管理相关 */
    //用于分页查询或者高级查询管理员信息
    @GetMapping("/queryAdminList")
    public Response queryAdminList(@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit,
                                  User user) {
        PageInfo<User> pageInfo = userService.queryAdminList(page, limit,
                user.getRealName(),
                user.getType(),
                user.getOrder());
        long num = pageInfo.getTotal();
        if(num > 0){
            return Response.ok(num, pageInfo.getList());
        }
        return Response.fail("查询用户信息失败！");
    }

    //用于分页查询或者高级查询管理员信息
    @GetMapping("/queryAllAdminList")
    public Response queryAllAdminList() {
        List<User> list = userService.queryAllAdminList();
        if(!list.isEmpty()){
            return Response.ok(list);
        }
        return Response.fail("查询用户信息失败！");
    }

    // 用于删除管理员
    @DeleteMapping("deleteAdminById")
    public Response deleteAdminById(Integer adminId) {
        if(userService.deleteAdminById(adminId)){
            return Response.ok();
        }
        return Response.fail("删除用户失败！");
    }

    // 用于编辑管理员状态
    @PostMapping("/editAdminStatus")
    public Response editAdminStatus(@RequestBody AdminStatus adminStatus) {
        if(userService.editAdminStatus(adminStatus)){
            return Response.ok();
        }
        return Response.fail("修改管理员状态失败！");
    }

    // 用于添加管理员
    @PostMapping("/addAdmin")
    public Response addAdmin(@RequestBody User user) {
        if(userService.addAdmin(user)){
            return Response.ok();
        }
        return Response.fail("添加用户失败！");
    }

    // 用于查询所有的社长
    @GetMapping("/queryAllCaptainInfo")
    public Response queryAllCaptainInfo() {
        return Response.ok(userService.queryAllCaptainInfo());
    }
}
