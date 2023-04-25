package com.example.chatapplication.custom;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserDetailCustom extends UserDetails {

    Long accountId();
    List<Long> ids();
}
