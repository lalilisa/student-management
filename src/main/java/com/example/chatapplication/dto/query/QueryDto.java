package com.example.chatapplication.dto.query;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class QueryDto {
    // số lượng data tối đa 1 trang

    int pageSize;

    // trang thứ n

    int pageNumber;


    // thuộc tính để tìm kiếm
    private String[] property;

    // danh sách thuộc tính để sắp xếp

    private String[] sort;

    // keyword tìm kiếm

    private String search;

    //filter truyền vào 1 chuỗi json string  VD: {"username":"trimai"}
//    @Parameter(hidden = true)
    private String filter;

    //Chuyển filter thành Map

    private Map<String,Object> filters;

    //convert string filter sang json
    public void setFilters() throws JsonProcessingException {
        if(this.filter!=null && this.filter.length()>0) {
            ObjectMapper objectMapper = new ObjectMapper();
            this.filters = objectMapper.readValue(filter, Map.class);
        }
    }
}
