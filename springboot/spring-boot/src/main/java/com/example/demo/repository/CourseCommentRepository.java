package com.example.demo.repository;

import com.example.demo.entity.CourseComment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseCommentRepository  extends CrudRepository<CourseComment, Integer>{


}
