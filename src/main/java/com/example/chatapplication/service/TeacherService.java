package com.example.chatapplication.service;

import com.example.chatapplication.domain.Teacher;
import com.example.chatapplication.dto.request.UpdateUserInfoDto;
import com.example.chatapplication.dto.response.TeacherInfor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeacherService {
        TeacherInfor getInforTeacher(Long id);
        Page<Teacher> findAll(Pageable pageable);
        Teacher updateInfor(Long id,UpdateUserInfoDto updateUserInfoDto);
}
