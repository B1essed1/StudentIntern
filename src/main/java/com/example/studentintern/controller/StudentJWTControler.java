package com.example.studentintern.controller;


import com.example.studentintern.entity.Student;
import com.example.studentintern.model.LoginModel;
import com.example.studentintern.repository.StudentRepository;
import com.example.studentintern.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/intern")
@Slf4j
public class StudentJWTControler {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final StudentRepository studentRepository;

    public StudentJWTControler(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, StudentRepository studentRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.studentRepository = studentRepository;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginModel loginModel){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginModel.getUsername(), loginModel.getPassword()));

        Student user  = studentRepository.findByUsername(loginModel.getUsername());
        log.info("user =====> {}" , user);
        if (user == null){
            throw new UsernameNotFoundException("Bu foydalanuvch mavjut emas");
        }
        String token = tokenProvider.createToken(user.getUsername(), user.getRoles());
        Map<Object, Object> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("token", token);

        return ResponseEntity.ok(map);
    }
}
