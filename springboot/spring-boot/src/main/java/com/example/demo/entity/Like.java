package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Like{

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    private String courseName;
    private String tearchName;

}
