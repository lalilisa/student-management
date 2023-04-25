package com.example.chatapplication.service.impl;

import com.example.chatapplication.common.Category;
import com.example.chatapplication.custom.exception.GeneralException;
import com.example.chatapplication.domain.Points;
import com.example.chatapplication.domain.Student;
import com.example.chatapplication.domain.StudentClass;
import com.example.chatapplication.repo.PointRepository;
import com.example.chatapplication.repo.StudentClassRepository;
import com.example.chatapplication.repo.StudentRepository;
import com.example.chatapplication.service.ExcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelServiceIpml implements ExcelService {
    private final static String tempalte="template_point.xlsx";
    private final static String dic="templates";

    private final PointRepository pointRepository;
    private final StudentClassRepository studentClassRepository;
    private final StudentRepository studentRepository;
    @Override
    public ByteArrayOutputStream exportTemplatePoint(Long classId) throws IOException {
        List<Long> studentIds= studentClassRepository.getListStudentIdByClassId(classId);
        List<Student> students=studentRepository.findAllById(studentIds);
        InputStream inputStream=new FileInputStream(dic+ File.separator+tempalte);
        XSSFWorkbook workbook;
        XSSFSheet sheet;
        ByteArrayOutputStream out;
        workbook=new XSSFWorkbook(inputStream);
        sheet=workbook.getSheetAt(0);
        for(int i=0;i<students.size();i++){
            XSSFRow row = sheet.createRow(i+1);
            Student student=students.get(0);
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
            row.createCell(0, CellType.STRING).setCellValue(i+1);
            row.createCell(1, CellType.STRING).setCellValue(student.getCode());
            row.createCell(2, CellType.STRING).setCellValue(student.getName());
            row.createCell(3, CellType.STRING).setCellValue(simpleDateFormat.format(student.getDob()));
        }
        out =new ByteArrayOutputStream();
        workbook.write(out);
        return  out;
    }

    @Override
    public  boolean importPointExcel(Long classId, MultipartFile file) {
        XSSFWorkbook  workbook;
        XSSFSheet sheet;
        try{
            workbook=new XSSFWorkbook(file.getInputStream());
            sheet=workbook.getSheetAt(0);
            int totalRow=sheet.getPhysicalNumberOfRows();
            for(int i=1;i<totalRow;i++){
                XSSFRow row=sheet.getRow(i);
                XSSFCell cellMSV=row.getCell(1);
                XSSFCell cellCC=row.getCell(4);
                XSSFCell cellBT=row.getCell(5);
                XSSFCell cellKT=row.getCell(6);
                XSSFCell cellTH=row.getCell(7);
                XSSFCell cellCK=row.getCell(8);
                Student student=studentRepository.findStudentByCode(cellMSV.getStringCellValue());
                StudentClass studentClass= studentClassRepository.getStudentClassByClassIdAndStudentId(classId,student.getId());
                if(studentClass==null){
                    throw  new GeneralException(Category.ErrorCodeEnum.INVALID_PARAMETER.name(),
                            String.format("Student %s is not in this class",cellMSV.getStringCellValue()));
                }
                Points points=pointRepository.findByClassIdAndStudentId(classId,student.getId());
                points.setCc(cellCC!=null?Double.parseDouble(cellCC.getRawValue()):null);
                points.setBt(cellBT!=null ?Double.parseDouble(cellBT.getRawValue()):null);
                points.setTh(cellTH!=null ?Double.parseDouble(cellTH.getRawValue()) :null);
                points.setKt(cellKT!=null ? Double.parseDouble(cellKT.getRawValue()):null);
                points.setCk(cellCK!=null ?Double.parseDouble(cellCK.getRawValue()):null);
                pointRepository.save(points);

            }
        }
        catch (IOException exception){
            return false;
        }
        return true;
    }


}
