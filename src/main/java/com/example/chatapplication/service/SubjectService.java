package com.example.chatapplication.service;

import com.example.chatapplication.domain.Subject;
import com.example.chatapplication.dto.response.StudentPointResponse;
import com.example.chatapplication.dto.response.SubjectInfo;

import java.util.List;

public interface SubjectService {

    List<Subject> getSubjectByListIds(List<Long> ids);
    List<SubjectInfo> getSubjectOfTeacher(Long teacherId, Integer term, String season);
    List<StudentPointResponse> getSubjectOfStudent(Long studentId, Integer term, String season);
}
