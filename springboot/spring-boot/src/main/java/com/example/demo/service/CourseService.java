package com.example.demo.service;

import com.example.demo.entity.Course;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


//中间类
@RestController
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseService {
    private final CourseRepository repository;

    public boolean existById(int id)
    {
        return repository.existsById(id);
    }
    public boolean deleteById(int id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
    public boolean exists(Course user) {
        return repository.existsByTeacherNameAndCourseName(user.getTeacherName(), user.getCourseName());
    }

    public Course insert(Course user) {
        return repository.save(user);
    }

    public Iterable<Course> getAllCourse(){
        return repository.findAll();
    }

    public String getNameById(int id){
        Optional<Course> tt=repository.findById(id);
        return tt.get().getCourseName();
    }

}
