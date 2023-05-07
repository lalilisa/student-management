package com.example.chatapplication.service.impl;

import com.example.chatapplication.common.Category;
import com.example.chatapplication.domain.Account;
import com.example.chatapplication.domain.Student;
import com.example.chatapplication.domain.Teacher;
import com.example.chatapplication.dto.request.CreateAccountDto;
import com.example.chatapplication.dto.request.LoginRequest;
import com.example.chatapplication.dto.response.AccountServiceResponse;
import com.example.chatapplication.repo.AccountRepository;
import com.example.chatapplication.repo.StudentRepository;
import com.example.chatapplication.repo.TeacherRepository;
import com.example.chatapplication.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class AccountServiceIpml implements AccountService {
    private final AccountRepository accountRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final TeacherRepository teacherRepository;
    @Override
    public AccountServiceResponse login(LoginRequest request){
        Account account=accountRepository.findAccountByUsername(request.getUsername());
        if(account==null)
            return null;
        if(!passwordEncoder.matches(request.getPassword(),account.getPassword()))
            return null;
        Student student=studentRepository.findStudentByAccountId(account.getId());
        try {
            if(student!=null)
                return  AccountServiceResponse.builder()
                        .code(student.getCode())
                        .userId(student.getId())
                        .username(account.getUsername())
                        .role(student.getRole())
                        .build();
            Teacher  teacher=teacherRepository.findTeacherByAccountId(account.getId());
            return  AccountServiceResponse.builder()
                    .code(teacher.getCode())
                    .userId(teacher.getId())
                    .username(account.getUsername())
                    .role(teacher.getRole())
                    .build();
        }
        catch (Exception exception){

            System.out.println(exception);
        }
      return null;
    }

    @Override
    public Account register(CreateAccountDto dto) {
        dto.setPassword(hashPassword(dto.getPassword()));
        Account account=Account.builder()
                            .username(dto.getCode())
                            .password(dto.getPassword())
                            .build();
        Account newAccount=accountRepository.save(account);
        if(dto.getAccountType()== Category.Role.STUDENT){
            initInforStudent(newAccount,dto.getEmail());
        }
        if(dto.getAccountType()== Category.Role.SUBJECT_TEACHER){
            initInforTeacher(newAccount,dto.getEmail());
        }
        return newAccount;
    }


    public String hashPassword(String plainText){
        int salt = 10;
        BCryptPasswordEncoder bCryptPasswordEncoder =
                new BCryptPasswordEncoder(salt, new SecureRandom());
        return bCryptPasswordEncoder.encode(plainText);
    }

    void initInforStudent(Account account,String email){
        Student student=Student.builder()
                                .accountId(account.getId())
                                .code(account.getUsername())
                                .email(email)
                                .role(Category.Role.STUDENT)
                                .gender(1)
                                .build();
        studentRepository.save(student);
    }
    void initInforTeacher(Account account,String email){
        Teacher teacher=Teacher.builder()
                .accountId(account.getId())
                .code(account.getUsername())
                .email(email)
                .role(Category.Role.SUBJECT_TEACHER)
                .gender(1)
                .build();
        teacherRepository.save(teacher);
    }
}
