package com.example.chatapplication.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDto {

    private String code;
    private String name;
    private Integer credit;
    private Integer precentcc;
    private Integer precentbt;
    private Integer precentth;
    private Integer precentkt;
    private Integer precentck;
}
