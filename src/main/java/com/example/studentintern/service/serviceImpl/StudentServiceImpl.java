package com.example.studentintern.service.serviceImpl;

import com.example.studentintern.entity.Role;
import com.example.studentintern.entity.Student;
import com.example.studentintern.repository.RoleRepository;
import com.example.studentintern.repository.StudentRepository;
import com.example.studentintern.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService, UserDetailsService {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";


    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository repository;

    public StudentServiceImpl(StudentRepository studentRepository, PasswordEncoder passwordEncoder, RoleRepository repository) {
        this.studentRepository = studentRepository;

        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Student student = studentRepository.findByUsername(username);

        log.info("password password password  {}",student.getPassword());       /* if (student == null){
            log.error("Student not found in database");
            throw new UsernameNotFoundException("Student not found in database");
        } else {
            log.info("Student  found in database {}" , student.getUsername() +"  " + student.getPassword());

        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        student.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role.getName())));
        log.info(" authorities  {}" ,authorities);

        return new User(student.getUsername(), student.getPassword(),authorities);*/

        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return student.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
            }

            @Override
            public String getPassword() {
                return student.getPassword();
            }

            @Override
            public String getUsername() {
                return student.getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }


    @Override
    public Student save(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        List<Role> role = repository.findAllByNameIn((ArrayList<String>) student.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        student.setRoles(new HashSet<>(role));
        return studentRepository.save(student);
    }


    @Override
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Student getStudentsById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) throw new RuntimeException("Student Not Found");
        else
            return student.get();
    }

    @Override
    public Student updateStudents(Long id, Student student) {


        Optional<Student> student2 = studentRepository.findById(id);
        Student student1 = student2.get();

        if (student.getUsername() != null) {
            student1.setUsername(student.getUsername());
        }
        if (student.getCreatedTime() != null) {
            student1.setCreatedTime(student.getCreatedTime());
        }
        if (student.getPassword() != null) {
            student1.setPassword(student.getPassword());
        }
        if (student.getSecondName() != null) {
            student1.setSecondName(student.getSecondName());
        }
        save(student1);

        return student1;
    }

    public boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public void excelToStudents(MultipartFile exelfile) throws IOException, ParseException {
        XSSFWorkbook workbook = new XSSFWorkbook(exelfile.getInputStream());

        XSSFSheet sheets = workbook.getSheetAt(0);
        for (Sheet sheet : workbook) {

            for (Row row : sheet) {
                Student student = new Student();
                student.setUsername(row.getCell(0).getStringCellValue());
                student.setSecondName(row.getCell(1).getStringCellValue());
                Double aDouble = row.getCell(2).getNumericCellValue();
                student.setCreatedTime(aDouble.toString());
                student.setPassword(row.getCell(3).getStringCellValue());
                student.setLocation(row.getCell(4).getStringCellValue());
                save(student);
            }
        }
    }
}
