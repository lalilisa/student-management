package com.example.chatapplication.domain;


import com.example.chatapplication.common.Category;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "teacher")
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Teacher extends Audiant{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Category.Role role;

    @Column(name = "account_id")
    private Long accountId;
}
