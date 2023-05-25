package com.example.demo.service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Course;
import com.example.demo.entity.Post;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentService {
    private final CommentRepository repository;
    public Iterable<Comment> getAll(){
            return repository.findAll();
    }
    public int getUserIDById(int id)
    {
        Optional<Comment> tt=repository.findById(id);
        return tt.get().getUserId();
    }
    public int getPostIDById(int id)
    {
        Optional<Comment> tt=repository.findById(id);
        return tt.get().getPostId();
    }

}
