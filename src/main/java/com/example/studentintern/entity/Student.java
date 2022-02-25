package com.example.studentintern.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String secondName;

    private String createdTime;

    private String password;

    private String location;

    @Lob
    private Byte[] image ;

    @OneToMany(cascade = CascadeType.ALL, mappedBy ="student")
    @JsonManagedReference(value = "schedule")
    private List<Schedules> schedules = new ArrayList<>();

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    } ,fetch = FetchType.EAGER)

    @JoinTable(
            name = "student_roles",
            joinColumns = @JoinColumn(
                    name = "student_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id"
            )
    )
    private Collection<Role>  roles  = new ArrayList<>();

}
