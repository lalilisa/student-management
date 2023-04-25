package com.example.chatapplication.dto.response;

import com.example.chatapplication.common.Category;
import com.example.chatapplication.domain.Classes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentInfor {

    protected Long studentId;

    protected String code;

    private String name;

    private String address;

    private String phone;

    private Date dob;

    private Integer gender;

    private Category.Role role;

}
