package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

//实体类
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    @Column(name = "userId")
    private int userId;
    private boolean gender;
    private String userName;
    private String password;
    private boolean admin;
}
