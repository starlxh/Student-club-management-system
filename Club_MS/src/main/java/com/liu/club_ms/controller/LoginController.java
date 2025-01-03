package com.liu.club_ms.controller;

import com.liu.club_ms.jwt.JWUtil;
import com.liu.club_ms.model.User;
import com.liu.club_ms.service.MailService;
import com.liu.club_ms.service.RedisService;
import com.liu.club_ms.service.UserService;
import com.liu.club_ms.util.Date2Str;
import com.liu.club_ms.util.Response;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private UserService userService;

    @Resource
    private MailService mailService;

    @Resource
    private RedisService redisService;

    //登录验证
    @PostMapping("/loginIn")
    public Map<String, Object> loginIn(@RequestBody User user){
        User user1 = userService.queryUserByEmailPwd(user);
        Map<String, Object> response = new HashMap<>();
        if(user1 != null){
            response.put("token", JWUtil.generateToken(user1));
            response.put("code", 20000);
        }else {
            response.put("code", 300);
            response.put("message", "用户名或者密码错误!");
        }
        return response;
    }

    @PostMapping("/sendCaptcha")
    public Response sendCaptcha(String email,
                                @RequestParam Integer type,
                                String token,
                                HttpServletResponse response) {
        if(type == null) return Response.fail("发生验证码失败");
        if(type == 0 || type == 4){
            if(userService.emailIsExist(email)){
                return Response.fail(600, "当前邮箱已被注册！");
            }
        }else if (type == 2) {
            email = userService.getEmailByUserId(JWUtil.getUserId(token));
        }
        else if(type == 1){
            if(!(userService.emailIsExist(email))){
                return Response.fail("该邮箱还未被注册");
            }
        }
        else{
            return Response.fail("发送验证码失败");
        }

        try {
            // 生成验证码
            String captcha = mailService.generateCaptcha(6);

            // 发送邮件
            mailService.sendCaptcha(email, "验证码邮件", captcha);

            // 存储到 Redis，有效期 5 分钟
            redisService.saveCaptcha(email, captcha, 300);

            response.addHeader("token", token);
            response.setHeader("Access-Control-Expose-Headers", "token");

            return Response.ok();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.fail("发生验证码失败！");
        }
    }

    @PostMapping("/register")
    public Response register(@RequestBody User user, @RequestParam String captcha) {
        String storedCaptcha = redisService.getCaptcha(user.getEmail());
        System.out.println("验证码：" + storedCaptcha);
        if (storedCaptcha != null && storedCaptcha.equals(captcha)) {
            redisService.deleteCaptcha(user.getEmail()); // 验证成功后删除验证码
        } else {
            return Response.fail("验证码错误或已过期！");
        }

        user.setCreateTime(Date2Str.getNowDate(new Date()));
        user.setType(0);
        user.setImage("/images/user/default.jpg");
        if (userService.addUser(user))
            return Response.ok();

        return Response.fail("注册失败！");
    }

    //通过token获取管理员对象和角色信息
    @GetMapping("/queryUserInfo")
    public Map<String, Object> queryUserInfo(String token){
        Map<String, Object> response = new HashMap<>();

        Integer type = JWUtil.getType(token);
        List<String> list = new ArrayList<>();

        if(type == 2){
            list.add("Super");
        }else if(type == 1){
            list.add("Admin");
        }else if(type == 0){
            list.add("User");
        }

        response.put("code", 20000);
        Map<String, Object> data = new HashMap<>();
        data.put("roles", list);
        data.put("id", JWUtil.getUserId(token));
        response.put("data", data);
        return response;
    }

    //退出
    @PostMapping("/loginOut")
    public Map<String, Object> logout(){
        Map<String, Object> response = new HashMap<>();
        response.put("code", 20000);
        return response;
    }

    // 用于找回密码
    @PostMapping("/findPassword")
    public Response editUser(@RequestBody User user, String captcha) {
        System.out.println(captcha);
        if(captcha != null){
            String storedCaptcha = redisService.getCaptcha(user.getEmail());
            System.out.println("验证码：" + storedCaptcha);
            if (storedCaptcha != null && storedCaptcha.equals(captcha)) {
                redisService.deleteCaptcha(user.getEmail()); // 验证成功后删除验证码
            } else {
                return Response.fail("验证码错误或已过期！");
            }
        }
        user.setUserId(userService.queryUserIdByEmail(user.getEmail()));
        if(userService.editUser(user)){
            return Response.ok();
        }
        return Response.fail("修改用户信息失败！");
    }
}
