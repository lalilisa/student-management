package com.example.chatapplication.dto.response;

import lombok.Data;

@Data
public class AbtractStudentInfo {
    protected Long classId;
    protected String classCode;
    protected Long subjectId;
    protected String subjectCode;
    protected  String subjectName;
    protected Integer credit;
    protected Integer term;
    protected String season;
}
