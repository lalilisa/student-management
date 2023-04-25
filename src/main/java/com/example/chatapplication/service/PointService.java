package com.example.chatapplication.service;

import com.example.chatapplication.dto.request.PointDto;
import com.example.chatapplication.dto.response.StudentPointResponse;

public interface PointService {

    StudentPointResponse updatePoint(Long classId, PointDto pointDto);
}
