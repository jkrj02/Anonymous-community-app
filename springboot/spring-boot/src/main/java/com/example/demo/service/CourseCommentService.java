package com.example.demo.service;


import com.example.demo.entity.Comment;
import com.example.demo.entity.CourseComment;

import com.example.demo.entity.Post;
import com.example.demo.repository.CourseCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseCommentService {
    private final CourseCommentRepository repository;
    public Iterable<CourseComment> getAll() {
            return repository.findAll();
    }
    public Iterable<CourseComment> findByCourseId(int courseId) {
        return repository.findByCourseId(courseId);
    }

    public int getUserIDById(int id)
    {
        Optional<CourseComment> tt=repository.findById(id);
        return tt.get().getUserId();
    }
    public CourseComment insert(CourseComment a) {
        return repository.save(a);
    }
    public boolean deleteById(int id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

}
