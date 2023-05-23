package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.myLike;
import com.example.demo.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LikeService {
    private final LikeRepository repository;

    public Iterable<myLike> getAll() {
        return repository.findAll();
    }
    public myLike insert(myLike a) {
        return repository.save(a);
    }
    public boolean delete(myLike a){
        myLike tt = repository.findByUserIdAndPostIdAndCommentIdAndCourseCommentId(a.getUserId(), a.getPostId(), a.getCommentId(), a.getCourseCommentId());
        repository.delete(tt);
        return true;
    }
}


