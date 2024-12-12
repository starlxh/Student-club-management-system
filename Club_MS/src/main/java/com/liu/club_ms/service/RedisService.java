package com.liu.club_ms.service;

public interface RedisService {
    public void saveCaptcha(String key, String value, long timeoutSeconds);

    public String getCaptcha(String key);

    public void deleteCaptcha(String key);
}
