package com.example.chatapplication.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubjectInfo {
    private Long classId;
    private String classCode;
    private Long subjectId;
    private String subjectCode;
    private  String subjectName;
    private Integer credit;
    private Integer term;
    private String season;
}
