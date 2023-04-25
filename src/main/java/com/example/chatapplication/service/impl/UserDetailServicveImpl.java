package com.example.chatapplication.service.impl;

import com.example.chatapplication.common.Category;
import com.example.chatapplication.custom.UserDetailCustom;
import com.example.chatapplication.custom.UserSecurityCustom;
import com.example.chatapplication.domain.*;
import com.example.chatapplication.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserDetailServicveImpl implements UserDetailsService {
    private final AccountRepository accountRepository;

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final TeacherClassRepository teacherClassRepository;
    private final StudentClassRepository studentClassRepository;
    @Override
    public UserDetailCustom loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account=accountRepository.findAccountByUsername(username);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        Student student=studentRepository.findStudentByAccountId(account.getId());
        Teacher teacher=teacherRepository.findTeacherByAccountId(account.getId());
        Category.Role role=null;
        List<Long> ids=new ArrayList<>();
        if(student!=null){
            role=student.getRole();
            List<StudentClass> studentClasses = studentClassRepository.findAllByStudentId(student.getId());
            studentClasses.stream().forEach(studentSub -> {
                ids.add(studentSub.getClassId());
            });
        }
        else {
            role=teacher.getRole();
            List<TeacherClass> teacherClasses = teacherClassRepository.findAllByTeacherId(teacher.getId());
            teacherClasses.stream().forEach(teacherClass -> {
                ids.add(teacherClass.getClassId());
            });
        }
        grantedAuthorities.add(new SimpleGrantedAuthority(role.name()));
        return new UserSecurityCustom(
                account.getUsername(),
                account.getPassword(),grantedAuthorities,ids);
    }
}
