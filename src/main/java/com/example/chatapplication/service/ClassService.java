package com.example.chatapplication.service;

import com.example.chatapplication.domain.Student;
import com.example.chatapplication.dto.response.StudentPointResponse;

import java.util.List;

public interface ClassService {
    List<StudentPointResponse> getStudentInClass(Long idClass);
}
