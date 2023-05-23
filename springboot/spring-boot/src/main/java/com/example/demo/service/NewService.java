package com.example.demo.service;

import com.example.demo.entity.New;
import com.example.demo.entity.User;
import com.example.demo.repository.NewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NewService {


    private final NewRepository repository;

    public Iterable<New> getAll() {
        return repository.findAll();
    }
    public Iterable<New> getByUserId(int id) {
        return repository.findByUserId(id);
    }
    public boolean update(New a) {
        repository.save(a);
        return true;
    }

}

