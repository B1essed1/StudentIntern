package com.example.studentintern.service.serviceImpl;

import com.example.studentintern.entity.Student;
import com.example.studentintern.repository.StudentRepository;
import com.example.studentintern.service.StudentService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";


    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student save(Student student) {

        return   studentRepository.save(student);
    }



    @Override
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Student getStudentsById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) throw  new RuntimeException("Student Not Found");
        else
        return student.get();
    }

    @Override
    public Student updateStudents(Long id ,Student student) {


        Optional<Student> student2 = studentRepository.findById(id);
        Student student1 = student2.get();

            if (student.getName() != null){ student1.setName(student.getName());}
            if (student.getCreatedTime() != null){ student1.setCreatedTime(student.getCreatedTime());}
            if (student.getCode() != null){ student1.setCode(student.getCode());}
            if (student.getSecondName() != null) {student1.setSecondName(student.getSecondName());}
            save(student1);

        return student1;
    }


    public  boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public  void excelToStudents(MultipartFile exelfile) throws IOException, ParseException {
        XSSFWorkbook workbook = new XSSFWorkbook(exelfile.getInputStream());

        XSSFSheet sheets = workbook.getSheetAt(0);
        for (Sheet sheet : workbook) {

            for (Row row : sheet) {
                Student student = new Student();
                student.setName(row.getCell(0).getStringCellValue());
                student.setSecondName(row.getCell(1).getStringCellValue());
                Double aDouble = row.getCell(2).getNumericCellValue();
                student.setCreatedTime(aDouble.toString());
                student.setCode( row.getCell(3).getStringCellValue());
                student.setLocation(row.getCell(4).getStringCellValue());
                save(student);

            }

        }

    }

}
