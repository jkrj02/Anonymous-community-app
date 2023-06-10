package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    @Column(name = "postId")
    private int postId;
    private int typeId;
    private int userId;
    private String userName;
    private String content;
    private int imageNum;
    private int commentCount;
    private int likeCount;
    private int dislikeCount;
    private int time;

}
