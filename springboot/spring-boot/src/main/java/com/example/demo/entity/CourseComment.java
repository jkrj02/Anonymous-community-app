package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseComment {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    @Column(name = "commentId")
    private int commentId;
    private int courseId;
    private int objectId;
    private int userId;
    private String userName;
    private  String othername;
    private String content;
    private int commentCount;
    private int likeCount;
    private int dislikeCount;
    private Date time;
    private boolean valid;

}
