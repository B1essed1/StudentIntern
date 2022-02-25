package com.example.studentintern.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Schedules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean isWork ;

    private LocalDate date;

    private LocalTime time;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id" , referencedColumnName = "id")
    @JsonBackReference(value = "schedule")
    private Student student;



}
