package com.example.demo.service;


import com.example.demo.entity.CourseComment;

import com.example.demo.repository.CourseCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseCommentService {
    private final CourseCommentRepository repository;
    public Iterable<CourseComment> getAll() {
            return repository.findAll();
    }


}