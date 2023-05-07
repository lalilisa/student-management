package com.example.chatapplication.controller;


import com.example.chatapplication.anotation.IsAdmin;
import com.example.chatapplication.common.Category;
import com.example.chatapplication.common.Utils;
import com.example.chatapplication.custom.exception.GeneralException;
import com.example.chatapplication.domain.Account;
import com.example.chatapplication.domain.Student;
import com.example.chatapplication.domain.Teacher;
import com.example.chatapplication.dto.request.ChangPasswordDto;
import com.example.chatapplication.dto.request.CreateAccountDto;
import com.example.chatapplication.dto.request.LoginRequest;
import com.example.chatapplication.dto.response.AccountServiceResponse;
import com.example.chatapplication.dto.response.LoginResponse;
import com.example.chatapplication.dto.response.ResponseMessage;
import com.example.chatapplication.repo.AccountRepository;
import com.example.chatapplication.repo.StudentRepository;
import com.example.chatapplication.repo.TeacherRepository;
import com.example.chatapplication.service.AccountService;
import com.example.chatapplication.service.StudentService;
import com.example.chatapplication.service.TeacherService;
import com.example.chatapplication.util.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/account")
@RequiredArgsConstructor
public class AccountController {
    @Value("${jwt.secret}")
    private String backendSecret;
    private final JwtTokenUtil tokenUtil;
    private final AccountService accountService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final StudentRepository studentRepository;
    private final JavaMailSender javaMailSender;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginRequest request){
        AccountServiceResponse response=accountService.login(request);
        if(response==null)
            throw new GeneralException(Category.ErrorCodeEnum.INVALID_PARAMETER.name(),"Username or password is wrong");
        String accessToken=tokenUtil.generateToken(response);
        return LoginResponse.builder().
                            username(response.getUsername()).
                            accessToken(accessToken).
                            userCode(response.getCode()).
                            userId(response.getUserId()).
                            build();
    }

    @Operation(description = "require role ADMIN")
    @Transactional
    @IsAdmin
    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody CreateAccountDto dto){
        Account account=accountService.register(dto);
        if(account!=null)
            return ResponseEntity.ok(Category.RegisterStatus.REGISTER_SUCCESS);
        return  ResponseEntity.ok(Category.RegisterStatus.REGISTER_FAIL);
    }

    @Operation(description = "require role LOGIN",summary = "Login")
    @Transactional
    @GetMapping ("me")
    public ResponseEntity<?> getMyInfo(Authentication authentication, HttpServletRequest request){
        if(authentication==null)
            throw new GeneralException(Category.ErrorCodeEnum.INVALID_FORMAT.name(),"Not Authenticated");
        UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        List<GrantedAuthority> authorities=  userDetails.getAuthorities().stream().collect(Collectors.toList());
        String header=request.getHeader("Authorization");
        String token=tokenUtil.getTokenFromHeader(header);
        Map<String,Object>claims=tokenUtil.getAllClaimsFromToken(token,backendSecret);
        Long userId= Long.parseLong((String) claims.get("userId"));
        if(authorities.contains(new SimpleGrantedAuthority(Category.Role.STUDENT.name()))){
            return ResponseEntity.ok(studentService.getInfoStudent(userId));
        }
        else {
            return ResponseEntity.ok(teacherService.getInforTeacher(userId));
        }

    }
    private final TeacherRepository teacherRepository;
    @Operation(description = "Don't need login",summary = "Quên mật khẩu")
    @Transactional
    @PostMapping ("forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) throws MessagingException {
        Long accountId=null;
        Teacher teacher=teacherRepository.findTeacherByEmail(email);
        Student student = studentRepository.findStudentByEmail(email);
        if(teacher!=null)
             accountId=teacher.getAccountId();
        if(student!=null)
           accountId=student.getAccountId();
        if(accountId==null)
            return ResponseEntity.ok(ResponseMessage.builder().message("Email ko tồn tại trên hệ thống").build());
        String newPass=Utils.autogenPassword();
        Account account = accountRepository.findById(accountId).orElse(null);
        account.setPassword(Utils.hashPassword(newPass));
        accountRepository.save(account);
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper =new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setText(String.format("Mật khẩu mới của bạn là %s . Tuyệt đối không được cung cấp cho người khác", newPass));
        javaMailSender.send(mimeMessage);
        return ResponseEntity.ok(ResponseMessage.builder().message("Vui lòng check Email của bạn").build());
    }

    @Operation(description = "require role LOGIN",summary = "Đổi mật khẩu")
    @Transactional
    @PostMapping ("change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangPasswordDto dto,Authentication authentication){
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        Account account=accountRepository.findAccountByUsername(userDetails.getUsername());
        boolean check=passwordEncoder.matches(dto.getOldPassword(),account.getPassword());
        if(!check){
            throw new GeneralException(Category.ErrorCodeEnum.INVALID_PARAMETER.name(),"Old password is not correct");
        }
        account.setPassword(Utils.hashPassword(dto.getNewPassword()));
        accountRepository.save(account);
        return ResponseEntity.ok(ResponseMessage.builder().message("Change password success").build());
    }



}
