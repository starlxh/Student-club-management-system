package com.liu.club_ms.jwt;


import com.alibaba.druid.util.StringUtils;
import com.liu.club_ms.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.util.Date;

public class JWUtil {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 30 * 60 * 1000;
    // 初始化 Logger
    private static final Logger logger = LoggerFactory.getLogger(JWUtil.class);
    // 生成 JWT Token
    public static String generateToken(User user) {
        return Jwts.builder()
                .claim("userId", user.getUserId())
                .claim("type", user.getType())
                .claim("userName", user.getUserName())
                .setIssuedAt(new Date()) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 设置过期时间（1小时）
                .signWith(SECRET_KEY) // 使用 HMAC SHA-256 签名
                .compact(); // 生成 Token
    }

    /**
     * 通用 Token 解析方法
     */
    private static Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token);
    }

    private static Claims parseClaims(String token) {
        return parseToken(token).getBody();
    }

    // 判断token是否有效
    public static int checkToken(String token) {
        if(StringUtils.isEmpty(token)) {
            return -1;
        }
        try {
            parseToken(token);
            return 20000;
        }catch (SignatureException e) {
            logger.error("不合法签名: " + e.getMessage());
            return 50008;
        }catch (ExpiredJwtException e) {
            logger.error("Token过期: " + e.getMessage());
            return 500014;
        }
        catch (Exception e) {
            logger.error("Token其他错误: " + e.getMessage());
            return 500020;
        }
    }

    public static int checkToken(HttpServletRequest request){
        String token = request.getHeader("token");
        return checkToken(token);
    }

//  获取用户Id
    public static Integer getUserId(String token){
        return parseClaims(token).get("userId", Integer.class);
    }

//  获取用户类型
    public static Integer getType(String token){
        return parseClaims(token).get("type", Integer.class);
    }

//  获取用户类型
    public static String getUserName(String token){
        return parseClaims(token).get("userName", String.class);
    }

}
