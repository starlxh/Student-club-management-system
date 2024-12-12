package com.liu.club_ms.service.impl;

import com.liu.club_ms.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void saveCaptcha(String key, String value, long timeoutSeconds) {
        redisTemplate.opsForValue().set(key, value, timeoutSeconds, TimeUnit.SECONDS);
    }

    @Override
    public String getCaptcha(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void deleteCaptcha(String key) {
        redisTemplate.delete(key);
    }
}
