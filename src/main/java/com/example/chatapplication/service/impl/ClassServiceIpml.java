package com.example.chatapplication.service.impl;

import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.Classes;
import com.example.chatapplication.domain.Points;
import com.example.chatapplication.domain.Student;
import com.example.chatapplication.domain.Subject;
import com.example.chatapplication.dto.response.StudentPointResponse;
import com.example.chatapplication.repo.*;
import com.example.chatapplication.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassServiceIpml implements ClassService {

    private final ClassRepository classRepository;
    private final StudentRepository studentRepository;
    private final StudentClassRepository studentClassRepository;
    private final SubjectRepository subjectRepository;
    private final PointRepository pointRepository;
    @Override
    public List<StudentPointResponse> getStudentInClass(Long idClass) {
        List<Long> idsStudent= studentClassRepository.getListStudentIdByClassId(idClass);
        List<Student>students= studentRepository.findAllById(idsStudent);
        return students.stream().map(student -> {
            Classes classes=classRepository.findById(idClass).orElse(null);
            Subject subject=subjectRepository.findById(classes.getSubjectId()).orElse(null);
            Points points=pointRepository.findByClassIdAndStudentId(classes.getId(),student.getId());
            if(points==null)
                points=new Points();
            Double base10=Utils.caculateFinalExamPoint(points,subject);
            Double base4=Utils.convertPointBase10ToBase4(base10);
            StudentPointResponse studentPointResponse= StudentPointResponse.builder()
                    .studentId(student.getId())
                    .name(student.getName())
                    .ck( points.getCk())
                    .kt(points.getKt())
                    .cc(points.getCc())
                    .th(points.getTh())
                    .bt(points.getBt())
                    .base4(points.getCk()==null? null: base4)
                    .bass10(points.getCk()==null? null:base10)
                    .charater(points.getCk()==null? null:Utils.convertPointBase4ToCharacter(base4))
                    .build();
            studentPointResponse.setSubjectId(subject.getId());
            studentPointResponse.setClassCode(classes.getCode());
            studentPointResponse.setClassId(classes.getId());
            studentPointResponse.setSubjectCode(subject.getCode());
            studentPointResponse.setCredit(subject.getCredit());
            studentPointResponse.setSubjectName(subject.getName());
            studentPointResponse.setTerm(classes.getTerm());
            studentPointResponse.setSeason(classes.getSeason());
            return studentPointResponse;
         }).collect(Collectors.toList());
    }
}
