package com.example.chatapplication.config;


import com.example.chatapplication.service.impl.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Properties;

@Configuration

public class MailConfig {
    @Autowired
    private JavaMailSender mailSender;

    @Bean
    public void configSendMail() {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.debug", false);
        ((EmailServiceImpl) this.mailSender).setHost("smtp.gmail.com");
        ((EmailServiceImpl) this.mailSender).setPort(587);
        ((EmailServiceImpl) this.mailSender).setUsername("maivantri309@gmail.com");
        ((EmailServiceImpl) this.mailSender).setPassword("gqyvovdwrrdycblw");
        ((EmailServiceImpl) this.mailSender).setJavaMailProperties(properties);
    }
}
