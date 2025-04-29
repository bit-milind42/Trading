package com.milind.service;

import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.internet.MimeMessage;

public class EmailService {

    private JavaMailSender javaMailSender;

    public void senderVerificationOtpEmail(String email, String otp){
        
        MimeMessage mimeMessage = javaMailSender.createMimeMessage(); 
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,"utf-8");

        String subject="Verify OTP"; 
        String text="Your verification code is "+otp; 

        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text); 
        mimeMessageHelper.setTo(email);

        try{
            javaMailSender.send(mimeMessage);
        }
        catch(MailException e){
            throw new MailSendException(e.getMessage());
        }
    }
}
