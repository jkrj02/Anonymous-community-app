package com.example.demo.repository;

import com.example.demo.entity.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Integer> {
    boolean existsByTeacherNameAndCourseName(String teacherName,String courseName);//是否存在

}
