package com.ultimatum.authcrud.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender, ResourceLoader resourceLoader) {
        this.mailSender = mailSender;
    }

    /**
     * Envoyer un mail
     * @param to : destinateur
     * @param subject : sujet
     * @param body : le message
     */
    public boolean sendEmail(String to, String subject, String body) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);
            return true;
        
        } catch (MessagingException e) {
            System.out.println("error : "+ e);
            return false;
        }
    }

}

