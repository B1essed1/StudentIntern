package com.example.studentintern.controller;


import com.example.studentintern.entity.Student;
import com.example.studentintern.service.StudentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;



@RestController
@RequestMapping("api/intern/")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;

    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id){

        return ResponseEntity.ok(studentService.getStudentsById(id));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteStudentById(@PathVariable("id") Long id){
        studentService.deleteById(id);
        return ResponseEntity.ok("successfully deleted");
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



    @PutMapping("update/student/{id}")
    public ResponseEntity<?> updateStudentById(@PathVariable("id") Long id , @RequestBody Student student){

        Student student1 = studentService.updateStudents(id,student);


        return ResponseEntity.ok(student1);
    }
    @Scope()
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
    public ResponseEntity<?> addFromExelToDb(@RequestParam("file") MultipartFile multipartFile) throws IOException, ParseException {

        studentService.excelToStudents(multipartFile);

      return ResponseEntity.ok("success");
    }
}
