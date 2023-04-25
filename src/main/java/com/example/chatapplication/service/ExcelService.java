package com.example.chatapplication.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface ExcelService {
    ByteArrayOutputStream exportTemplatePoint(Long classId) throws IOException, InvocationTargetException, IllegalAccessException;

    boolean importPointExcel(Long classId, MultipartFile file);
}
