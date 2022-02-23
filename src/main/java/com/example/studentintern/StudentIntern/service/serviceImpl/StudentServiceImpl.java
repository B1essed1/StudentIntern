package com.example.studentintern.StudentIntern.service.serviceImpl;

import com.example.studentintern.StudentIntern.entity.Student;
import com.example.studentintern.StudentIntern.repository.StudentRepository;
import com.example.studentintern.StudentIntern.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private  final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Student student) {

        repository.save(student);
    }

    @Override
    public void saveImage(Long id , MultipartFile file) {

    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Student getStudentsById(Long id) {
        Optional<Student> student = repository.findById(id);
        return student.get();
    }

    @Override
    public Student updateStudents(MultipartFile file) {
        return null;
    }
}
