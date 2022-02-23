package com.example.studentintern.StudentIntern.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String secondName;

    private Date date;

    private Double code;

    private String location;

    @Lob
    private Byte[] image ;

}
