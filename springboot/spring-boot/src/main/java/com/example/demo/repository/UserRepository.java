package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//数据库类
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    boolean existsByUserName(String userName);

    User findByUserNameAndPassword(String userName, String password);
}
