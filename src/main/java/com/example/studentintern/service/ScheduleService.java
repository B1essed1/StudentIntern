package com.example.studentintern.service;


import com.example.studentintern.entity.Schedules;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    public Schedules save(Schedules reg);

    List<Schedules> getSchedulesByDate(LocalDate date,Long id);

}
