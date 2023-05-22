package com.example.demo.service;

import com.example.demo.entity.Course;
import com.example.demo.entity.User;
import com.example.demo.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//中间类
@RestController
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseService {
    private final CourseRepository repository;

    public boolean exists(Course user) {
        return repository.existsByTeacherNameAndCourseName(user.getTeacherName(), user.getCourseName());
    }

    public Course insert(Course user) {
        return repository.save(user);
    }

    public Iterable<Course> getAllCourse(){
        return repository.findAll();
    }


}
