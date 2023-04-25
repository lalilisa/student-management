package com.example.chatapplication.service.impl;

import com.example.chatapplication.common.Category;
import com.example.chatapplication.custom.exception.GeneralException;
import com.example.chatapplication.domain.Teacher;
import com.example.chatapplication.dto.request.UpdateUserInfoDto;
import com.example.chatapplication.dto.response.TeacherInfor;
import com.example.chatapplication.repo.ClassRepository;
import com.example.chatapplication.repo.SubjectRepository;
import com.example.chatapplication.repo.TeacherRepository;
import com.example.chatapplication.repo.TeacherClassRepository;
import com.example.chatapplication.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceIpml implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final ClassRepository classRepository;
    private final TeacherClassRepository teacherClassRepository;
    private final SubjectRepository subjectRepository;
    @Override
    public TeacherInfor getInforTeacher(Long id) {
        Teacher teacher=teacherRepository.findById(id).orElse(null);
        List<Long> idClasses= teacherClassRepository.getListIdClass(id);
        return TeacherInfor.builder()
                .id(teacher.getId())
                .address(teacher.getAddress())
                .code(teacher.getCode())
                .dob(teacher.getDob())
                .name(teacher.getName())
                .gender(teacher.getGender())
                .role(teacher.getRole())
                .phone(teacher.getPhone())
                .build();
    }

    @Override
    public Page<Teacher> findAll(Pageable pageable) {
        return teacherRepository.findAll(pageable);
    }

    @Override
    public Teacher updateInfor(Long id, UpdateUserInfoDto updateUserInfoDto) {
        Teacher teacher=teacherRepository.findById(id).orElse(null);
        if(teacher==null)
            throw new GeneralException(Category.ErrorCodeEnum.INVALID_PARAMETER.name(),"teacher not exist");
        teacher.setAddress(updateUserInfoDto.getAddress());
        teacher.setDob(updateUserInfoDto.getDob());
        teacher.setPhone(updateUserInfoDto.getPhone());
        teacher.setName(updateUserInfoDto.getName());
        return teacherRepository.save(teacher);
    }
}
