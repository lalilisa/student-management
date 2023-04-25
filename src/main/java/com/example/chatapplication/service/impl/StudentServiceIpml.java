package com.example.chatapplication.service.impl;

import com.example.chatapplication.domain.Student;
import com.example.chatapplication.dto.response.StudentInfor;
import com.example.chatapplication.repo.ClassRepository;
import com.example.chatapplication.repo.StudentRepository;
import com.example.chatapplication.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service

public class StudentServiceIpml implements StudentService {

    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;

    public StudentServiceIpml(StudentRepository studentRepository, ClassRepository classRepository) {
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
    }

    @Override
    public StudentInfor getInfoStudent(Long id) {
        Student student=studentRepository.findById(id).orElse(null);
        return StudentInfor.builder()
                .studentId(student.getId())
                .address(student.getAddress())
                .code(student.getCode())
                .dob(student.getDob())
                .name(student.getName())
                .gender(student.getGender())
                .role(student.getRole())
                .phone(student.getPhone())
                .build();
    }


}
