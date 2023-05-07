package com.example.chatapplication.controller;

import com.example.chatapplication.anotation.IsAdmin;
import com.example.chatapplication.anotation.YourSelfTeacherOrAdmin;
import com.example.chatapplication.common.Category;

import com.example.chatapplication.domain.Account;
import com.example.chatapplication.domain.Teacher;
import com.example.chatapplication.dto.request.UpdateUserInfoDto;
import com.example.chatapplication.dto.response.ResponseMessage;
import com.example.chatapplication.repo.AccountRepository;
import com.example.chatapplication.repo.TeacherRepository;
import com.example.chatapplication.service.SubjectService;
import com.example.chatapplication.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    private final TeacherRepository teacherRepository;
    
    private final AccountRepository accountRepository;

    private final SubjectService subjectService;

    @Operation(description = "require role ADMIN",summary = "Dánh sách tất cẩ teacher trong hệ thống")
    @IsAdmin
    @GetMapping("")
    public ResponseEntity<?> getInfoTeachers(Pageable pageable){

        return ResponseEntity.ok(teacherService.findAll(pageable));
    }

    @Operation(description = "Chỉ có giáo viên dạy lớp tương ứng hoặc ADMIN",summary = "Danh sách môn dạy của  GV")
    @YourSelfTeacherOrAdmin
    @GetMapping("/list-subject/{username}")
    public ResponseEntity<?> getListSubjectOfTeacher(
            @PathVariable String username,
            @RequestParam(name = "season",required = false) String season,
            @RequestParam(name = "term",required = false) Integer term
    ){
        Account account=accountRepository.findAccountByUsername(username);
        Teacher teacher=teacherRepository.findTeacherByAccountId(account.getId());
        return ResponseEntity.ok(subjectService.getSubjectOfTeacher(teacher.getId(),term,season));
    }
    @Operation(description = "require role ADMIN",summary = "Lấy thông tin giáo viên")
    @IsAdmin
    @GetMapping("{id}")
    public ResponseEntity<?> getInfoTeacher(@PathVariable Long id){

        return ResponseEntity.ok(teacherRepository.findById(id));
    }


    @Operation(summary = "cập nhật thông tin cá nhân GV")
    @PutMapping()
    public ResponseEntity<?> update(@RequestBody UpdateUserInfoDto updateUserInfoDto, Authentication authentication){
        UserDetails details=(UserDetails) authentication.getPrincipal();
        Account account=accountRepository.findAccountByUsername(details.getUsername());
        Teacher teacher=teacherRepository.findTeacherByAccountId(account.getId());
        teacher.setAddress( updateUserInfoDto.getAddress()!=null ? updateUserInfoDto.getAddress():teacher.getAddress());
        teacher.setName(updateUserInfoDto.getName() !=null? updateUserInfoDto.getName():teacher.getAddress());
        teacher.setDob(updateUserInfoDto.getDob() !=null ? updateUserInfoDto.getDob():teacher.getDob());
        teacher.setPhone(updateUserInfoDto.getPhone() !=null ? updateUserInfoDto.getAddress():teacher.getAddress());
        return ResponseEntity.ok(teacherRepository.save(teacher));
    }



    @Operation(description = "require role ADMIN",summary = "Xóa giáo viên")
    @IsAdmin
    @Transactional
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        ResponseMessage responseMessage=new ResponseMessage();
        try {
            accountRepository.deleteById(teacherRepository.findById(id).get().getAccountId());
            responseMessage.setMessage(Category.Status.SUCCESS.name());
        }
        catch (Exception e){
            responseMessage.setMessage(Category.Status.FAIL.name());
        }
        return ResponseEntity.ok(responseMessage);
    }
}
