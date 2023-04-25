package com.example.chatapplication.service;

import com.example.chatapplication.domain.Account;
import com.example.chatapplication.dto.request.CreateAccountDto;
import com.example.chatapplication.dto.request.LoginRequest;
import com.example.chatapplication.dto.response.AccountServiceResponse;

public interface AccountService {
    AccountServiceResponse login(LoginRequest request);
    Account register(CreateAccountDto dto);
}
