package com.example.studentintern.controller;

import com.example.studentintern.entity.Role;
import com.example.studentintern.model.RoleToSudent;
import com.example.studentintern.repository.RoleRepository;
import com.example.studentintern.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/internship/role")
public class RoleController {


    @Autowired
    private RoleRepository roleRepository;

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveRole(@RequestBody Role role){
        roleService.save(role);
     return    ResponseEntity.ok("success");
    }

    @PostMapping("/add/role")
    public ResponseEntity<?> addRoleToStudent(@RequestBody RoleToSudent role){
        log.info("{}  name should be here " ,role.getName());


        String name = role.getName();
        roleService.addRoleToUser(role.getId(), name);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/get")
    public ResponseEntity<?> getRoleByName(@RequestParam("name") String name){
        Role role = roleRepository.findByName(name);

        if (role == null) throw new RuntimeException("Runtime exception");
        return ResponseEntity.ok(role);
    }
}
