package com.example.demo.service;

import com.example.demo.entity.Post;
import com.example.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PostService {


    private final PostRepository repository;

    public boolean exist(int id)
    {
        return repository.existsById(id);
    }
    public int getUserIDById(int id) {
        Optional<Post> tt = repository.findById(id);
        return tt.get().getUserId();
    }

    public Iterable<Post> getAll() {
        return repository.findAll();
    }

    public Post insert(Post a) {
        return repository.save(a);
    }

    public boolean deleteById(int id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
    public String getPostContentById(int id){
        Optional<Post> tt=repository.findById(id);
        return tt.get().getContent();
    }
}



