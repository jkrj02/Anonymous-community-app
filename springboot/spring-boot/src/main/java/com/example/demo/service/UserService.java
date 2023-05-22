package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserRepository repository;

    public boolean exists(User user) {
        return repository.existsByUserName(user.getUserName());
    }
    public Iterable<User> getAllUsers(){
        return  repository.findAll();
    }
    public User findByNameAndPassword(User user) {
        System.out.println(user.getPassword());
        return repository.findByUserNameAndPassword(user.getUserName(), user.getPassword());

    }

    public User insert(User user) {
        return repository.save(user);
    }

    public boolean update(User user) {
        if (repository.existsByUserName(user.getUserName())) {
            return false;
        }
        repository.save(user);
        return true;
    }

    public boolean deleteById(int id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
