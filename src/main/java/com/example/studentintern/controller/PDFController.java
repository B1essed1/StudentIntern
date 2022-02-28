package com.example.studentintern.controller;


import com.example.studentintern.entity.Student;
import com.example.studentintern.service.PDFService;
import com.example.studentintern.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@Slf4j
@RequestMapping("/api/pdf")
public class  PDFController  {

    private final PDFService pdfService;
    private final StudentService studentService;

    public PDFController(PDFService pdfService, StudentService studentService) {
        this.pdfService = pdfService;
        this.studentService = studentService;
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<?> dateToPDF(@PathVariable("id") Long id,@RequestParam("date") String dat) throws FileNotFoundException {
        Student student = studentService.getStudentsById(id);

        pdfService.studentToPDF(student,dat);

        return ResponseEntity.ok("dfasdfds");
    }



}
