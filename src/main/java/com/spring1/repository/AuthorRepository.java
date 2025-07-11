package com.spring1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring1.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer>{

}
