package com.example.chatapplication.dto.response;

import com.example.chatapplication.common.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountServiceResponse {
    private Long userId;
    private String username;
    private String code;
    private Category.Role role;
}
