package com.example.studentintern.repository;

import com.example.studentintern.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long> {

    Role findByName(String name);
    List<Role> findAllByNameIn(ArrayList<String> name);
}
