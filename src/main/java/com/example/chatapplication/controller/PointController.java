package com.example.chatapplication.controller;


import com.example.chatapplication.anotation.IsTeacherClass;
import com.example.chatapplication.anotation.TeacherOrAdmin;
import com.example.chatapplication.dto.request.PointDto;
import com.example.chatapplication.repo.PointRepository;
import com.example.chatapplication.service.ExcelService;
import com.example.chatapplication.service.PointService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("api/point")
@RequiredArgsConstructor
public class PointController {

    private final PointRepository pointRepository;
    private final PointService pointService;
    private final ExcelService excelService;

    @Operation(summary = "Nhập điểm cho SV")
    @IsTeacherClass
    @PutMapping("addpoint/{classId}")
    public ResponseEntity<?> updatePoint(@PathVariable Long classId, @RequestBody PointDto pointDto){
        return ResponseEntity.ok(pointService.updatePoint(classId,pointDto));
    }

    @Operation(description = "Chỉ có giáo viên dạy lớp tương ứng hoặc ADMIN",summary = "Nhập điểm bằng file excel")
    @IsTeacherClass
    @PostMapping(value = "import/template-point/{classId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importPoint(@PathVariable Long classId,@ModelAttribute MultipartFile file){
        boolean check=excelService.importPointExcel(classId,file);
        if(check)
           return ResponseEntity.ok("Import success");
        return ResponseEntity.ok("Import fail");
    }

    @Operation(description = "Chỉ có giáo viên dạy lớp tương ứng hoặc ADMIN",summary = "Lấy file excel danh sách sinh viên trong 1 lớp")
    @IsTeacherClass
    @GetMapping("export/template-point/{classId}")
    public ResponseEntity<?> exportTemplatePoint(@PathVariable Long classId) throws IOException, InvocationTargetException, IllegalAccessException {
        String filename = "templatePoint.xlsx";
        ByteArrayOutputStream byteArrayOutputStream =excelService.exportTemplatePoint(classId);
        InputStreamResource file = new InputStreamResource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

    @PostMapping(value = "import/test",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importPoints(@ModelAttribute MultipartFile file){
        System.out.println(file.getOriginalFilename());
        return ResponseEntity.ok("Import fail");
    }
}
