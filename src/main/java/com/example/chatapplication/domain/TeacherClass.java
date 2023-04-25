package com.example.chatapplication.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "teacher_class")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherClass extends Audiant{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "teacher_id")
    private Long teacherId;
    @Column(name = "class_id")
    private Long classId;
}
