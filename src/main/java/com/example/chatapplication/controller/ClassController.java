package com.example.chatapplication.controller;

import com.example.chatapplication.anotation.IsAdmin;
import com.example.chatapplication.anotation.IsTeacherClass;
import com.example.chatapplication.common.Category;
import com.example.chatapplication.custom.exception.GeneralException;
import com.example.chatapplication.domain.Classes;
import com.example.chatapplication.domain.Points;
import com.example.chatapplication.domain.StudentClass;
import com.example.chatapplication.domain.TeacherClass;
import com.example.chatapplication.dto.request.ClassDto;
import com.example.chatapplication.dto.request.StudentClassDto;
import com.example.chatapplication.dto.request.TeacherClassDto;
import com.example.chatapplication.dto.response.ResponseMessage;
import com.example.chatapplication.repo.ClassRepository;
import com.example.chatapplication.repo.PointRepository;
import com.example.chatapplication.repo.StudentClassRepository;
import com.example.chatapplication.repo.TeacherClassRepository;
import com.example.chatapplication.service.ClassService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("api/class")
@RequiredArgsConstructor
public class ClassController {

    private final StudentClassRepository studentClassRepository;
    private final ClassRepository classRepository;
    private final ClassService classService;
    private final TeacherClassRepository teacherClassRepository;
    private final PointRepository pointRepository;


    @Operation(description = "require role ADMIN")
    @IsAdmin
    @Transactional
    @GetMapping("")
    public ResponseEntity<?> findAll(Pageable pageable){
        return ResponseEntity.ok(classRepository.findAll(pageable));
    }

    @Operation(description = "Chỉ có giáo viên dạy lớp tương ứng hoặc ADMIN",summary = "Lấy danh sách sinh viên trong 1 lớp học")
    @IsTeacherClass
    @Transactional
    @GetMapping("{id}/list-students")
    public ResponseEntity<?> findOne(@PathVariable Long id){
        return ResponseEntity.ok(classService.getStudentInClass(id));
    }

    @Operation(description = "require role ADMIN",summary = "Thêm sinh viên vào 1 lớp học")
    @IsAdmin
    @Transactional
    @PostMapping("add-student-class")
    public ResponseEntity<?> addStudentForClass(@RequestBody StudentClassDto studentClassDto){
        StudentClass checkInClass=studentClassRepository.getStudentClassByClassIdAndStudentId(studentClassDto.getClassId(),studentClassDto.getStudentId());
        if(checkInClass==null)
            throw new GeneralException(Category.ErrorCodeEnum.INVALID_FORMAT.name(),"Student is added to this class");
        StudentClass studentClass =StudentClass
                                .builder()
                                .classId(studentClassDto.getClassId())
                                .studentId(studentClassDto.getStudentId())
                                .build();
        studentClassRepository.save(studentClass);
        Points points=Points.builder().classId(studentClassDto.getClassId())
                                        .studentId(studentClassDto.getStudentId())
                                        .build();
        pointRepository.save(points);
        return ResponseEntity.ok(ResponseMessage.builder().message("Add student success").build());
    }
    @Operation(description = "require role ADMIN",summary = "Thêm giáo viên vào 1 lớp học")
    @IsAdmin
    @Transactional
    @PostMapping("add-teacher-class")
    public ResponseEntity<?> addTeacherForClass(@RequestBody TeacherClassDto teacherClassDto){
        StudentClass checkInClass=studentClassRepository.getStudentClassByClassIdAndStudentId(teacherClassDto.getClassId(),teacherClassDto.getTeacherId());
        if(checkInClass==null)
            throw new GeneralException(Category.ErrorCodeEnum.INVALID_FORMAT.name(),"Teacher is added to this class");
        TeacherClass teacherClass =TeacherClass
                .builder()
                .classId(teacherClassDto.getClassId())
                .teacherId(teacherClassDto.getTeacherId())
                .build();
        teacherClassRepository.save(teacherClass);
        return ResponseEntity.ok(ResponseMessage.builder().message("Add student success").build());
    }

    @Operation(description = "require role ADMIN",summary = "tạo lớp học")
    @IsAdmin
    @Transactional
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody ClassDto classDto){
        Classes classes=Classes.builder()
                .code(classDto.getCode())
                .season(classDto.getSeason())
                .subjectId(classDto.getSubjectId())
                .term(classDto.getTerm())
                .build();
        return ResponseEntity.ok(classRepository.save(classes));
    }

    @Operation(description = "require role ADMIN",summary = "cập nhật lớp học")
    @IsAdmin
    @Transactional
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody ClassDto classDto){
        Classes oldClass=classRepository.findById(id).orElse(null);
        if(oldClass==null)
            throw new GeneralException(Category.ErrorCodeEnum.INVALID_PARAMETER.name(),"Not found classes");
        Classes classes=Classes.builder()
                .code(classDto.getCode()!=null ? classDto.getCode(): oldClass.getCode())
                .season(classDto.getSeason()!=null ? classDto.getCode(): oldClass.getSeason())
                .subjectId(classDto.getSubjectId()!=null ? classDto.getSubjectId(): oldClass.getSubjectId())
                .term(classDto.getTerm()!=null ? classDto.getTerm(): oldClass.getTerm())
                .build();
        return ResponseEntity.ok(classRepository.save(classes));

    }

    @Operation(description = "require role ADMIN",summary = "Xóa lớp học")
    @IsAdmin
    @Transactional
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        ResponseMessage responseMessage=new ResponseMessage();
        try {
            classRepository.deleteById(id);
            responseMessage.setMessage(Category.Status.SUCCESS.name());
        }
        catch (Exception e){
            responseMessage.setMessage(Category.Status.FAIL.name());
        }
        return ResponseEntity.ok(responseMessage);
    }
}
