package com.liu.club_ms.service.impl;

import com.liu.club_ms.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public String generateCaptcha(int length) {
        Random random = new Random();
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < length; i++) {
            captcha.append(random.nextInt(10)); // 生成数字验证码
        }
        return captcha.toString();
    }

    @Override
    public void sendCaptcha(String to, String subject, String captcha) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("studentclubmanager@qq.com"); // 发件人
        message.setTo(to);                   // 收件人
        message.setSubject(subject);         // 邮件标题
        message.setText("您的验证码是：" + captcha + "，请在5分钟内完成验证。"); // 邮件内容
        mailSender.send(message);
    }
}
