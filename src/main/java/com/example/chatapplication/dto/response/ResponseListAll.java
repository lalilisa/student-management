package com.example.chatapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseListAll {
    private int totalPage;
    private int currentPage;
    private int currentData;
    private Object data;
}