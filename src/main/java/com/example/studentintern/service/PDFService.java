package com.example.studentintern.service;

import com.example.studentintern.entity.Student;

import java.io.FileNotFoundException;

public interface PDFService {
    void studentToPDF(Student student, String date) throws FileNotFoundException;
}
