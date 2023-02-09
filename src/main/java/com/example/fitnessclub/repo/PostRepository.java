package com.example.fitnessclub.repo;

import com.example.fitnessclub.models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

    public interface PostRepository extends CrudRepository<Post, Long> {

        Post findByNames(String names);

        List<Post> findByNamesContains(String names);

    }

