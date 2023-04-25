package com.example.chatapplication.controller;

import com.example.chatapplication.anotation.IsAdmin;
import com.example.chatapplication.common.Category;
import com.example.chatapplication.custom.exception.GeneralException;
import com.example.chatapplication.domain.Subject;
import com.example.chatapplication.dto.request.SubjectDto;
import com.example.chatapplication.dto.response.ResponseMessage;
import com.example.chatapplication.repo.SubjectRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/subject")
@RequiredArgsConstructor
@Tag(name = "Subject Controller")
public class SubjectController {

    private final SubjectRepository subjectRepository;

    @Operation(summary = "Danh sách tất cả môn học, yêu cầu quyền ADMIN")
    @GetMapping("")
    public ResponseEntity<?> findAll(Pageable pageable){
        return ResponseEntity.ok(subjectRepository.findAll(pageable));
    }
    @Operation(summary = "tạo môn học, yêu cầu quyền ADMIN")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody SubjectDto subjectDto){
        Subject subject=Subject.builder()
                .code(subjectDto.getCode())
                .credit(subjectDto.getCredit())
                .name(subjectDto.getName())
                .precentcc(subjectDto.getPrecentcc())
                .precentbt(subjectDto.getPrecentbt())
                .precentth(subjectDto.getPrecentth())
                .precentck(subjectDto.getPrecentck())
                .precentkt(subjectDto.getPrecentkt())
                .build();
        return ResponseEntity.ok(subjectRepository.save(subject));
    }



    @GetMapping("{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id){

        return ResponseEntity.ok(subjectRepository.findById(id));
    }

    @Operation(summary = "sửa môn học, yêu cầu quyền ADMIN")
    @IsAdmin
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody SubjectDto subjectDto){
        Subject subject=subjectRepository.findById(id).orElse(null);
        if(subject==null)
            throw new GeneralException(Category.ErrorCodeEnum.INVALID_PARAMETER.name(),"Not found Subject");
        subject.setCode(subjectDto.getCode());
        subject.setName(subjectDto.getName());
        subject.setCredit(subjectDto.getCredit());
        subject.setPrecentcc(subjectDto.getPrecentcc());
        subject.setPrecentbt(subjectDto.getPrecentbt());
        subject.setPrecentth(subjectDto.getPrecentth());
        subject.setPrecentck(subjectDto.getPrecentck());
        subject.setPrecentkt(subjectDto.getPrecentkt());
        return ResponseEntity.ok(subjectRepository.save(subject));
    }
    @Operation(summary = "xóa môn học, yêu cầu quyền ADMIN")
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        ResponseMessage responseMessage=new ResponseMessage();
        try {
            subjectRepository.deleteById(id);
            responseMessage.setMessage(Category.Status.SUCCESS.name());
        }
        catch (Exception e){
            responseMessage.setMessage(Category.Status.FAIL.name());
        }
        return ResponseEntity.ok(responseMessage);
    }
}
