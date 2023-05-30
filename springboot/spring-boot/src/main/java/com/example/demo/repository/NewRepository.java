package com.example.demo.repository;

import com.example.demo.entity.New;
import com.example.demo.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface NewRepository extends CrudRepository<New, Integer> {
    Iterable<New> findByUserId(int userId);
    Iterable<New> findByOtherIdAndType(int otherId,int type);
    boolean existsByUserIdAndTypeAndOtherNameAndContentAndPostIdAndCourseId(int userId,int type,String otherName,String content,int postId,int courseId);
}
