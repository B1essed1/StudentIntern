package com.example.studentintern.service;


import com.example.studentintern.entity.Role;

public interface  RoleService {

    Role save(Role role);
    Role getRoleByName(String name );
    public void addRoleToUser(Long studentId ,String roleName);
}
