package com.example.demo.service;

import com.example.demo.entity.Mail;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//import org.thymeleaf.context.Context;
//import org.thymeleaf.spring6.SpringTemplateEngine;
//
//import jakarta.mail.internet.MimeMessage;
//import java.nio.charset.StandardCharsets;

public interface EmailService 
{
    void sendEmail(Mail mail) throws RuntimeException;
}
