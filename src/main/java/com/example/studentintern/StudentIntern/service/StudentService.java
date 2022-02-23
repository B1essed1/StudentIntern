package com.example.studentintern.StudentIntern.service;

import com.example.studentintern.StudentIntern.entity.Student;
import org.springframework.web.multipart.MultipartFile;

public interface StudentService {
    public void save(Student file);
    public void  saveImage(Long id,MultipartFile file);

    public void deleteById(Long id);
    public Student getStudentsById(Long id);
    public Student updateStudents(MultipartFile file);

}
