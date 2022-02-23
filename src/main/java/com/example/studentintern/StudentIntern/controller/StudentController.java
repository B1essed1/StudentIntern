package com.example.studentintern.StudentIntern.controller;


import com.example.studentintern.StudentIntern.entity.Student;
import com.example.studentintern.StudentIntern.repository.StudentRepository;
import com.example.studentintern.StudentIntern.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("api/intern/")
public class StudentController {

    private final StudentService studentService;
    private final StudentRepository repository;

    public StudentController(StudentService studentService, StudentRepository repository) {
        this.studentService = studentService;
        this.repository = repository;
    }


    @PostMapping("save/students/info")
    public ResponseEntity<?> saveStudentsInfo(@RequestBody Student student){

        Student student1 = studentService.save(student);

        if (student1 == null) throw new RuntimeException("cannot save debitors");

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(student1.getId()).toUri();


        return ResponseEntity.ok(location);
    }

    @PostMapping("save/students/image/{id}")
    public  ResponseEntity<?> addImage(@PathVariable Long id,
                                       @RequestParam("file")MultipartFile multipartFile) throws IOException {
        Student student = studentService.getStudentsById(id);
        Byte[] image= new Byte[multipartFile.getBytes().length];
        int i= 0;
        for (byte b: multipartFile.getBytes()) {
            image[i++] = b;
        }
        student.setImage(image);
        studentService.save(student);

        return ResponseEntity.ok("success");
    }

    @PostMapping("save/students/xls")
    public ResponseEntity<?> addFromExelToDb(@RequestParam("file") MultipartFile multipartFile) throws IOException {


            List<Student> tutorials = studentService.excelToStudents(multipartFile.getInputStream());
            repository.saveAll(tutorials);


      return ResponseEntity.ok("succes");
    }
}
