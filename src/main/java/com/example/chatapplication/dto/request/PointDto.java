package com.example.chatapplication.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointDto {
    private Long studentId;
    private Double cc;
    private Double bt;
    private Double th;
    private Double kt;
    private Double ck;
}
