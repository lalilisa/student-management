package com.example.chatapplication.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "subject")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subject extends Audiant{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "credit")
    private   Integer credit;

    @Column(name = "precentcc")
    private Integer precentcc;

    @Column(name = "precentbt")
    private Integer precentbt;

    @Column(name = "precentth")
    private Integer precentth;

    @Column(name = "precentkt")
    private Integer precentkt;

    @Column(name = "precentck")
    private Integer precentck;

}
