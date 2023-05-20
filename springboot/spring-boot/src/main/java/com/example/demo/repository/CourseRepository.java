package com.example.demo.repository;

import com.example.demo.entity.course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<course, Integer> {
    boolean existsByTearchName(String tearchName);//是否存在

    course findByTearchNameAndCourseName(String tearchName, String courseName);
}
