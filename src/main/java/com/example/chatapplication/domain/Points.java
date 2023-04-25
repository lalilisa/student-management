package com.example.chatapplication.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "point")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Points extends Audiant{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "class_id")
    private Long classId;

    @Column(name = "cc")
    private Double cc;

    @Column(name = "bt")
    private Double bt;

    @Column(name = "th")
    private Double th;

    @Column(name = "kt")
    private Double kt;

    @Column(name = "ck")
    private Double ck;

}
