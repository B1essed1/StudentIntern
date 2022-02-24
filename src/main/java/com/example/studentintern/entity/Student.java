package com.example.studentintern.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String secondName;

    private String createdTime;

    private String code;

    private String location;

    @Lob
    private Byte[] image ;

    @OneToMany(cascade = CascadeType.ALL, mappedBy ="student")
    @JsonManagedReference
    private List<Schedules> schedules = new ArrayList<>();



}
