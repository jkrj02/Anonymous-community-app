package com.example.demo.repository;

import com.example.demo.entity.Course;
import com.example.demo.entity.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PostRepository extends CrudRepository<Post, Integer>{


}
