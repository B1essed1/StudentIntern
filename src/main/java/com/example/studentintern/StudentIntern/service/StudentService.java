package com.example.studentintern.StudentIntern.service;

import com.example.studentintern.StudentIntern.entity.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface StudentService {
    public Student save(Student file);
    public  boolean hasExcelFormat(MultipartFile file);
    public List<Student> excelToStudents(InputStream is);
    public void deleteById(Long id);
    public Student getStudentsById(Long id);
    public Student updateStudents(MultipartFile file);

}
