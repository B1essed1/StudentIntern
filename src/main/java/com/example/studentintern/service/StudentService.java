package com.example.studentintern.service;

import com.example.studentintern.entity.Schedules;
import com.example.studentintern.entity.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

public interface StudentService {

    public Student save(Student file);
    public void excelToStudents(MultipartFile multipartFile) throws IOException, ParseException;
    public void deleteById(Long id);
    public Student getStudentsById(Long id);
    public Student updateStudents(Long id ,Student file);

}
