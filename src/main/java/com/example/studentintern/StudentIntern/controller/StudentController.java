package com.example.studentintern.StudentIntern.controller;


import com.example.studentintern.StudentIntern.entity.Student;
import com.example.studentintern.StudentIntern.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/student/")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping("save/students/info")
    public ResponseEntity<?> saveStudentsInfo(@RequestBody Student student){

        service.save(student);

        return ResponseEntity.ok("success");
    }

}
