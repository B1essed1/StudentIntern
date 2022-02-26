package com.example.studentintern.service.serviceImpl;


import com.example.studentintern.entity.Role;
import com.example.studentintern.entity.Student;
import com.example.studentintern.repository.RoleRepository;
import com.example.studentintern.service.RoleService;
import com.example.studentintern.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final StudentService studentService;

    public RoleServiceImpl(RoleRepository roleRepository, StudentService studentService) {
        this.roleRepository = roleRepository;

        this.studentService = studentService;
    }


    @Override
    public Role save(Role role) {
        log.info("{}  saved   to database ", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public void addRoleToUser(Long studentId, String roleName) {

        Student student = studentService.getStudentsById(studentId);
        Role role = roleRepository.findByName(roleName);

        if (student !=null && role!=null){
            student.getRoles().add(role);
            studentService.save(student);
            log.info("{} added to    {} ", role.getName() , student.getUsername());
        } else throw new RuntimeException("Student or Role could not find in DB give proper name and id ");
    }
}
