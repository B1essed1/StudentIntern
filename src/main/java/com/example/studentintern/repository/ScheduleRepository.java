package com.example.studentintern.repository;


import com.example.studentintern.entity.Schedules;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedules , Long> {

    @Query("select s from Schedules s where s.student.id=:id and s.date=:date")
    List<Schedules> getAllByDate(@Param("date") LocalDate date, @Param("id") Long id);

}
