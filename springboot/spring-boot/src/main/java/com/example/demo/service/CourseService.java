package com.example.demo.service;

import com.example.demo.entity.course;
import com.example.demo.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseService {
    private final CourseRepository repository;

    public boolean exists(course user) {
        return repository.existsByTearchName(user.getTearchName());
    }

    public course findByNameAndPassword(course user) {
       // System.out.println(user.getPassword());
        //System.out.println(repository.findByNameAndPassword(user.getName(), user.getPassword()).getPassword());
        return repository.findByTearchNameAndCourseName(user.getTearchName(), user.getCourseName());

    }

    public boolean insert(course user) {
        repository.save(user);
        return true;
    }

    public boolean update(course user) {
        if (repository.findById(user.getId())==null) {
            return false;
        }
        repository.save(user);
        return true;
    }

    public boolean deleteById(int id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
