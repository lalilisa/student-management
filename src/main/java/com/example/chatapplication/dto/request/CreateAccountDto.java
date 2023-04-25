package com.example.chatapplication.dto.request;

import com.example.chatapplication.common.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountDto {

    private String code;
    private String password;
    private String email;
    private Category.Role accountType;
}
