package com.example.studentintern.service.serviceImpl;


import com.example.studentintern.entity.Schedules;
import com.example.studentintern.repository.ScheduleRepository;
import com.example.studentintern.service.ScheduleService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository repository;

    public ScheduleServiceImpl(ScheduleRepository repository) {
        this.repository = repository;
    }


    @Override
    public Schedules save(Schedules reg) {
        return  repository.save(reg);
    }

    @Override
    public List<Schedules> getSchedulesByDate(LocalDate date, Long id) {

        List<Schedules> schedules = repository.getAllByDate(date,id);
        return schedules;
    }
}
