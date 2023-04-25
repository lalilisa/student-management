package com.example.chatapplication.service.impl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl extends JavaMailSenderImpl implements JavaMailSender {
}