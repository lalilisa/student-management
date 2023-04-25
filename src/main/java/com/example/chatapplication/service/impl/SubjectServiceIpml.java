package com.example.chatapplication.service.impl;

import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.Classes;
import com.example.chatapplication.domain.Points;
import com.example.chatapplication.domain.Student;
import com.example.chatapplication.domain.Subject;
import com.example.chatapplication.dto.response.StudentPointResponse;
import com.example.chatapplication.dto.response.SubjectInfo;
import com.example.chatapplication.repo.*;
import com.example.chatapplication.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectServiceIpml implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final ClassRepository classRepository;
    private final TeacherClassRepository teacherClassRepository;
    private final StudentClassRepository studentClassRepository;
    private final PointRepository pointRepository;
    private final StudentRepository studentRepository;
    @Override
    public List<Subject> getSubjectByListIds(List<Long> ids) {
        return subjectRepository.getSubjectByIdIsIn(ids);
    }

    @Override
    public List<SubjectInfo> getSubjectOfTeacher(Long teacherId,Integer term,String season) {
        List<Long> idClasses;
        if ("all".equals(season)) {
            idClasses =teacherClassRepository.getListIdClass(teacherId);
        } else {
            idClasses = teacherClassRepository.getListIdClassInTermAndSeason(teacherId, term, season);
        }
        List<Long> idSubjects=classRepository.getAllIdSubject(idClasses);
        List <Subject>subjects = getSubjectByListIds(idSubjects);
        List<Classes> classes=classRepository.findAllById(idClasses);
        return subjects.stream().map(subject -> convertToSubjectInfor(subject,classes)).collect(Collectors.toList());
    }

    @Override
    public List<StudentPointResponse> getSubjectOfStudent(Long studentId, Integer term, String season) {
        List<Long> idClasses;
        if ("all".equals(season)) {
            idClasses = studentClassRepository.getListClassIdByStudentId(studentId);
        }
        else
         idClasses = studentClassRepository.getListIdClass(studentId, term, season);
        List<Long> idSubjects=classRepository.getAllIdSubject(idClasses);
        List <Subject>subjects = getSubjectByListIds(idSubjects);
        List<Classes> classes=classRepository.findAllById(idClasses);
        Student student=studentRepository.findById(studentId).orElse(null);
        List<StudentPointResponse>pointResponses= subjects.stream().map(subject -> convertToStudentPointResponse(subject,classes,student)).collect(Collectors.toList());
        pointResponses.sort((o1, o2) -> {
            if (o1.getSeason().equals(o2.getSeason()))
                return o2.getTerm() - o1.getTerm();
            return o2.getSeason().compareTo(o1.getSeason());
        });
        return  pointResponses;
    }


    public StudentPointResponse convertToStudentPointResponse(Subject domain,List<Classes> classes,Student student){
        Classes clazz=classes.stream().filter(classes1 -> classes1.getSubjectId().equals(domain.getId())).findFirst().orElse(null);
        Points newPoint = pointRepository.findByClassIdAndStudentId(clazz.getId(),student.getId());
        System.out.println(newPoint);
        Double base10=newPoint!=null ? Utils.caculateFinalExamPoint(newPoint,domain) : 0;
        Double base4=Utils.convertPointBase10ToBase4(base10);
        if(newPoint==null)
            newPoint =new Points();
        StudentPointResponse studentPointResponse=  StudentPointResponse.builder()
                .bt(newPoint.getBt())
                .cc(newPoint.getCc())
                .kt(newPoint.getKt())
                .th(newPoint.getTh())
                .studentId(student.getId())
                .ck(newPoint.getCk())
                .base4(newPoint.getCk()==null? null: base4)
                .bass10(newPoint.getCk()==null? null:base10)
                .charater(newPoint.getCk()==null? null: Utils.convertPointBase4ToCharacter(base4))
                .build();
        studentPointResponse.setSubjectId(domain.getId());
        studentPointResponse.setClassCode(clazz.getCode());
        studentPointResponse.setSubjectCode(domain.getCode());
        studentPointResponse.setSubjectName(domain.getName());
        studentPointResponse.setTerm(clazz.getTerm());
        studentPointResponse.setName(student.getName());
        studentPointResponse.setClassCode(clazz.getCode());
        studentPointResponse.setClassId(clazz.getId());
        studentPointResponse.setCredit(domain.getCredit());
        studentPointResponse.setSeason(clazz.getSeason());
        return studentPointResponse;
    }

    public SubjectInfo convertToSubjectInfor(Subject domain,List<Classes> classes){
        Classes clazz=classes.stream().filter(classes1 -> classes1.getSubjectId().equals(domain.getId())).findFirst().orElse(null);
        Subject subject=subjectRepository.findById(clazz.getSubjectId()).orElse(null);
        SubjectInfo subjectInfo=new SubjectInfo();
        subjectInfo.setSubjectId(subject.getId());
        subjectInfo.setClassCode(clazz.getCode());
        subjectInfo.setSubjectCode(subject.getCode());
        subjectInfo.setSubjectName(subject.getName());
        subjectInfo.setTerm(clazz.getTerm());
        subjectInfo.setSeason(clazz.getSeason());
        subjectInfo.setClassId(clazz.getId());
        return subjectInfo;
    }
}
