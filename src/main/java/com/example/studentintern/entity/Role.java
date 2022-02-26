package com.example.studentintern.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
@Getter
@Setter
public class Role implements Serializable {

    @Id @NotNull
    private String name;

}
