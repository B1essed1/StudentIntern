package com.example.studentintern.controller;


import com.example.studentintern.dtos.ScheduleDto;
import com.example.studentintern.entity.Schedules;
import com.example.studentintern.model.TodaysReg;
import com.example.studentintern.service.ScheduleService;
import com.example.studentintern.service.StudentService;
import com.example.studentintern.utils.Utils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/intern/scheduling")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final StudentService studentService;
    private final RabbitTemplate template;


    public ScheduleController(ScheduleService scheduleService, StudentService studentService, RabbitTemplate template) {
        this.scheduleService = scheduleService;
        this.studentService = studentService;
        this.template = template;
    }

    @PostMapping("/reg")
    public ResponseEntity<?> addRegister(@RequestBody ScheduleDto reg){

        Schedules schedules = reg.convertToSchedules(scheduleService,studentService);
        scheduleService.save(schedules);

        template.convertAndSend(Utils.EXCHANGE, Utils.ROUTING_KEY,schedules);
        return   ResponseEntity.ok("success !!");
    }

    @GetMapping("/test")
    public ResponseEntity<?> addToDb(){
        RestTemplate template = new RestTemplate();

        Schedules schedules = new  Schedules();

        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        Random random = new Random();
        Boolean isWork = random.nextBoolean();

        schedules.setTime(localTime);
        schedules.setDate(localDate);
        schedules.setIsWork(isWork);

        return ResponseEntity.ok(schedules);
    }


    @GetMapping("/get/daily")
    @Transactional
    public ResponseEntity<?> getDailySchedule(@RequestBody TodaysReg reg){

        List<Schedules> list = scheduleService.getSchedulesByDate(reg.getDate(),reg.getId());

        return ResponseEntity.ok(list);
    }
}
