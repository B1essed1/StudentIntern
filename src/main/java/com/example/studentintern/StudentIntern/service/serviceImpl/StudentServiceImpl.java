package com.example.studentintern.StudentIntern.service.serviceImpl;

import com.example.studentintern.StudentIntern.entity.Student;
import com.example.studentintern.StudentIntern.repository.StudentRepository;
import com.example.studentintern.StudentIntern.service.StudentService;
<<<<<<< HEAD
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
=======
>>>>>>> origin/master
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
<<<<<<< HEAD
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
=======
>>>>>>> origin/master
import java.util.Optional;

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
        return student.get();
    }

    @Override
    public Student updateStudents(MultipartFile file) {
        return null;
    }


    public  boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public  List<Student> excelToStudents(InputStream is) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet("intern");
            Iterator<Row> rows = sheet.iterator();
            List<Student> students = new ArrayList<>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Student student = new Student();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            student.setName(currentCell.getStringCellValue());                            break;
                        case 1:
                            student.setSecondName(currentCell.getStringCellValue());
                            break;
                        case 2:
                            student.setDate(currentCell.getDateCellValue());
                            break;
                        case 3:
                            student.setCode( currentCell.getNumericCellValue());
                            break;
                        case 4:
                            student.setLocation( currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                students.add(student);
            }
            workbook.close();
            return students;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
