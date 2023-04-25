package com.example.chatapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentPointResponse extends SubjectInfo{
    private Long studentId;
    private String name;
    private Double cc;
    private Double bt;
    private Double th;
    private Double kt;
    private Double ck;
    private Double base4;
    private Double bass10;
    private String charater;

}
