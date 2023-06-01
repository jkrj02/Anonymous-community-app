package com.example.demo.repository;

import com.example.demo.entity.Dislike;
import com.example.demo.entity.myLike;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface DislikeRepository extends CrudRepository<Dislike, Integer> {
    Dislike findByUserIdAndPostIdAndCommentIdAndCourseCommentId(int userId, int postId, int commentId, int courseCommentId);
    boolean existsByUserIdAndPostIdAndCommentIdAndCourseCommentId(int userId,int postId,int commentId,int courseCommentId);

}
