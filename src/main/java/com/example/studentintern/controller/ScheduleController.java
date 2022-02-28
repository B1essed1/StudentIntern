package com.example.studentintern.controller;


import com.example.studentintern.dtos.ScheduleDto;
import com.example.studentintern.dtos.TodaysRegisters;
import com.example.studentintern.entity.Schedules;
import com.example.studentintern.model.TodaysReg;
import com.example.studentintern.service.ScheduleService;
import com.example.studentintern.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/intern/scheduling")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final StudentService studentService;


    public ScheduleController(ScheduleService scheduleService, StudentService studentService) {
        this.scheduleService = scheduleService;
        this.studentService = studentService;
    }

    @PostMapping("/reg")
    public ResponseEntity<?> addRegister(@RequestBody ScheduleDto reg){

        Schedules schedules = reg.convertToSchedules(scheduleService,studentService);
        scheduleService.save(schedules);
      return   ResponseEntity.ok("succes");
    }

    @GetMapping("/get/daily")
    @Transactional
    public ResponseEntity<?> getDailySchedule(@RequestBody TodaysReg reg){

        List<Schedules> list = scheduleService.getSchedulesByDate(reg.getDate(),reg.getId());

        return ResponseEntity.ok(list);
    }
 }
