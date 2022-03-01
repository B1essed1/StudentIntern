package com.example.studentintern.dtos;

import com.example.studentintern.entity.Schedules;
import com.example.studentintern.entity.Student;
import com.example.studentintern.service.ScheduleService;
import com.example.studentintern.service.StudentService;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class   ScheduleDto {
     private Long id;
     private Boolean isWork;


     public Schedules convertToSchedules(ScheduleService scheduleService, StudentService studentService){
          Schedules schedules = new Schedules();
     return convertToSchedules( schedules , scheduleService,  studentService );
     }
     public Schedules convertToSchedules(Schedules schedules ,ScheduleService scheduleService, StudentService studentService){
          Student student = studentService.getStudentsById(id);
          schedules.setIsWork(isWork);

          LocalDate date = LocalDate.now();
          LocalTime time = LocalTime.now();

          schedules.setTime(time);
          schedules.setDate(date);
          schedules.setStudent(student);


          return schedules;
     }
}
