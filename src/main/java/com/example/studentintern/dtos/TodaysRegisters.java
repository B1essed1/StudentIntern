package com.example.studentintern.dtos;

import com.example.studentintern.entity.Schedules;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class TodaysRegisters {

    private LocalTime time;
    private Boolean isWork;
    private LocalDate date;

    private  List<Schedules>  schedules;
    public TodaysRegisters(List<Schedules> schedules){
        this.schedules = schedules;
    }




    public  List<TodaysRegisters> getAll()
    {
        List<TodaysRegisters> list = new ArrayList<>();

        for (Schedules registers: schedules ) {
            TodaysRegisters todaysRegisters = new TodaysRegisters();
            todaysRegisters.setDate(registers.getDate());
            todaysRegisters.setTime(registers.getTime());
            todaysRegisters.setIsWork(registers.getIsWork());
            list.add(todaysRegisters);
        }
        return list;
    }
}
