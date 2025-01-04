package com.liu.club_ms.service;

public interface MailService {
    public String generateCaptcha(int length);

    public void sendCaptcha(String to, String subject, String captcha);
}
