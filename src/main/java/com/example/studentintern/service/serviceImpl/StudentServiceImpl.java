package com.example.studentintern.service.serviceImpl;

import com.example.studentintern.entity.Student;
import com.example.studentintern.repository.StudentRepository;
import com.example.studentintern.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService, UserDetailsService {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";


    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentServiceImpl(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Student student =  studentRepository.findByUsername(username);

        if (student == null){
            log.error("Student not found in database");
            throw new UsernameNotFoundException("Student not found in database");
        } else {
            log.info("Student  found in database {}" , student.getUsername() +"  " + student.getPassword());

        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        student.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role.getName())));
        log.info(" authorities  {}" ,authorities);

        return new User(student.getUsername(), student.getPassword(),authorities);
    }



    @Override
    public Student save(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
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

            if (student.getUsername() != null){ student1.setUsername(student.getUsername());}
            if (student.getCreatedTime() != null){ student1.setCreatedTime(student.getCreatedTime());}
            if (student.getPassword() != null){ student1.setPassword(student.getPassword());}
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

            for (Row row : sheet){
                Student student = new Student();
                student.setUsername(row.getCell(0).getStringCellValue());
                student.setSecondName(row.getCell(1).getStringCellValue());
                Double aDouble = row.getCell(2).getNumericCellValue();
                student.setCreatedTime(aDouble.toString());
                student.setPassword( row.getCell(3).getStringCellValue());
                student.setLocation(row.getCell(4).getStringCellValue());
                save(student);
            }
        }
    }
}
