package com.example.chatapplication.service.impl;

import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.Classes;
import com.example.chatapplication.domain.Points;
import com.example.chatapplication.domain.Student;
import com.example.chatapplication.domain.Subject;
import com.example.chatapplication.dto.request.PointDto;
import com.example.chatapplication.dto.response.StudentPointResponse;
import com.example.chatapplication.repo.*;
import com.example.chatapplication.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointServiceIpml implements PointService {

    private final PointRepository pointRepository;
    private final ClassRepository classRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;


    public StudentPointResponse updatePoint(Long classId, PointDto pointDto){
        Points point=pointRepository.findByClassIdAndStudentId(classId,pointDto.getStudentId());
        point.setBt(pointDto.getCc()!=null?  pointDto.getCc():point.getCc());
        point.setBt(pointDto.getBt()!=null?  pointDto.getBt():point.getBt());
        point.setBt(pointDto.getKt()!=null?  pointDto.getKt():point.getKt());
        point.setBt(pointDto.getTh()!=null?  pointDto.getTh():point.getTh());
        point.setBt(pointDto.getCk()!=null?  pointDto.getCk():point.getCk());
        Points newPoint= pointRepository.save(point);
        Student student=studentRepository.findById(pointDto.getStudentId()).orElse(null);
        Classes classes=classRepository.findById(point.getClassId()).orElse(null);
        Subject subject=subjectRepository.findById(classes.getSubjectId()).orElse(null);
        Double base10=Utils.caculateFinalExamPoint(newPoint,subject);
        Double base4=Utils.convertPointBase10ToBase4(base10);
        StudentPointResponse studentPointResponse=  StudentPointResponse.builder()
                .name(student.getName())
                .studentId(student.getId())
                .bt(newPoint.getBt())
                .cc(newPoint.getCc())
                .kt(newPoint.getKt())
                .th(newPoint.getTh())
                .ck(newPoint.getCk())
                .base4(newPoint.getCk()==null? null: base4)
                .bass10(newPoint.getCk()==null? null:base10)
                .charater(newPoint.getCk()==null? null: Utils.convertPointBase4ToCharacter(base4))
                .build();
        studentPointResponse.setSubjectId(subject.getId());
        studentPointResponse.setClassCode(classes.getCode());
        studentPointResponse.setSubjectCode(subject.getCode());
        studentPointResponse.setSubjectName(subject.getName());
        studentPointResponse.setTerm(classes.getTerm());
        studentPointResponse.setSeason(classes.getSeason());
        return studentPointResponse;

    }
}
