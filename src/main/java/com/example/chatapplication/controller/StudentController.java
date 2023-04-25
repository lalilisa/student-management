package com.example.chatapplication.controller;

import com.example.chatapplication.anotation.IsAdmin;
import com.example.chatapplication.anotation.YourSeflStudentOrAdmin;
import com.example.chatapplication.common.Category;
import com.example.chatapplication.domain.Account;
import com.example.chatapplication.domain.Student;
import com.example.chatapplication.dto.request.UpdateUserInfoDto;
import com.example.chatapplication.dto.response.ResponseMessage;
import com.example.chatapplication.repo.AccountRepository;
import com.example.chatapplication.repo.StudentRepository;
import com.example.chatapplication.service.StudentService;
import com.example.chatapplication.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    private final StudentRepository studentRepository;

    private final AccountRepository accountRepository;

    private final SubjectService subjectService;

    @Operation(description = "require role ADMIN",summary = "Danh sách tất cả SV trong cả hệ thông")
    @IsAdmin
    @GetMapping("")
    public ResponseEntity<?> getAllStudents(Pageable pageable){

        return ResponseEntity.ok(studentRepository.findAll(pageable));
    }
    @Operation(description = "require role ADMIN",summary = "Lấy thông tin của 1 sinh viên")
    @IsAdmin
    @GetMapping("{id}")
    public ResponseEntity<?> getInfoStudent(@PathVariable Long id){

        return ResponseEntity.ok(studentService.getInfoStudent(id));
    }
    @Operation(description = "require role Login",summary = "Cập nhật thông tin cá nhân SV")
    @PutMapping()
    public ResponseEntity<?> update(@RequestBody UpdateUserInfoDto updateUserInfoDto, Authentication authentication){
        UserDetails details=(UserDetails) authentication.getPrincipal();
        Account account=accountRepository.findAccountByUsername(details.getUsername());
        Student student=studentRepository.findStudentByAccountId(account.getId());
        student.setAddress( updateUserInfoDto.getAddress()!=null ? updateUserInfoDto.getAddress():student.getAddress());
        student.setName(updateUserInfoDto.getName() !=null? updateUserInfoDto.getName():student.getAddress());
        student.setDob(updateUserInfoDto.getDob() !=null ? updateUserInfoDto.getDob():student.getDob());
        student.setPhone(updateUserInfoDto.getPhone() !=null ? updateUserInfoDto.getAddress():student.getAddress());
        return ResponseEntity.ok(studentRepository.save(student));
    }

    @Operation(description = "require role ADMIN",summary = "Xóa Sinh viên")
    @IsAdmin
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        ResponseMessage responseMessage=ResponseMessage.builder().build();
        try {
            studentRepository.deleteById(id);
            responseMessage.setMessage(Category.Status.SUCCESS.name());
        }
        catch (Exception e){
            responseMessage.setMessage(Category.Status.FAIL.name());
        }
        return ResponseEntity.ok(responseMessage);
    }

    @Operation(description = "require role ADMIN OR học sinh người sở hữu tài khoản",summary = "Lấy danh sách môn học của sinh viên")
    @YourSeflStudentOrAdmin
    @GetMapping("/list-subject/{username}")
    public ResponseEntity<?> getListSubjectOfStudent(
            @PathVariable String username,
            @RequestParam(name = "season" ,required = false) String season,
            @RequestParam(name = "term", required = false) Integer term){

        Account account=accountRepository.findAccountByUsername(username);
        Student student=studentRepository.findStudentByAccountId(account.getId());
        return ResponseEntity.ok(subjectService.getSubjectOfStudent(student.getId(),term,season));
    }


}
