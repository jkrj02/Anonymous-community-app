package com.example.demo.repository;

import com.example.demo.entity.New;
import com.example.demo.entity.myLike;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface LikeRepository extends CrudRepository<myLike, Integer> {
    myLike findByUserIdAndPostIdAndCommentIdAndCourseCommentId(int userId,int postId,int commentId,int courseCommentId);
}
