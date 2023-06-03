package com.example.demo.service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Dislike;
import com.example.demo.entity.myLike;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.DislikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DislikeService {

    private final DislikeRepository repository;
    public Iterable<Dislike> getAll(){
        return repository.findAll();
    }
    public Dislike insert(Dislike a) {
        return repository.save(a);
    }
    public boolean delete(Dislike a){
        Dislike tt = repository.findByUserIdAndPostIdAndCommentIdAndCourseCommentId(a.getUserId(), a.getPostId(), a.getCommentId(), a.getCourseCommentId());
        if(tt==null){
            return false;
        }
        repository.delete(tt);
        return true;
    }
    public  boolean exist(Dislike a)
    {
        return repository.existsByUserIdAndPostIdAndCommentIdAndCourseCommentId(a.getUserId(), a.getPostId(), a.getCommentId(), a.getCourseCommentId());
    }


}
