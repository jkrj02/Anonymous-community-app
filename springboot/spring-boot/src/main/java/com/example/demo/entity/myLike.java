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
public class myLike {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    @Column(name = "likeId")
    private int likeId;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "post_id")
    private int postId;
    @Column(name = "comment_id")
    private int commentId;
    @Column(name = "course_comment_id")
    private int courseCommentId;
    private String info;

}
