package com.example.studentintern.repository;

import com.example.studentintern.entity.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student,Long> {
    Student findByUsername(String name);
}
