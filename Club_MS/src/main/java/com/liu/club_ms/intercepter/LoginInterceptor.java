package com.liu.club_ms.intercepter;


import com.liu.club_ms.jwt.JWUtil;
import com.liu.club_ms.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //拦截器进行判断放行
        if(request.getMethod().equalsIgnoreCase("OPTIONS")){
            return true;
        }

        //如果是图片
        if(request.getRequestURI().contains("images")){
            return true;
        }

        //获取token
        String token = request.getHeader("token");

        if(token == null || token.isEmpty()){
            return false;
        }else {
            System.out.println("token = " + token);
            //验证是否有效
            int res = JWUtil.checkToken(token);
            if (res == 20000){
                //token续期
                User user = new User();
                user.setUserId(JWUtil.getUserId(token));
                user.setType(JWUtil.getType(token));
                user.setUserName(JWUtil.getUserName(token));
                token=JWUtil.generateToken(user);
                response.addHeader("token", token);
                response.setHeader("Access-Control-Expose-Headers", "token");
                return true;
            }else if(res == 50008){
                response.setHeader("code", "50008");
                response.setHeader("message", "invalid token!");
                response.setHeader("Access-Control-Expose-Headers", "code,message");
                return false;
            }else if(res == 500014){
                response.setHeader("code", "500014");
                response.setHeader("message", "token is expired!");
                response.setHeader("Access-Control-Expose-Headers", "code,message");
                return false;
            }else {
                response.setHeader("code", "500020");
                response.setHeader("message", "other token error!");
                response.setHeader("Access-Control-Expose-Headers", "code,message");
                return false;
            }
        }
    }
}
