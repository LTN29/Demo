package com.spring1.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring1.model.Author;

public interface AuthorService {
	List<Author> getAllAuthors();

	Author getAuthorById(Integer id);

	Author createAuthor(Author author, MultipartFile file);

	Author updateAuthor(Integer id, Author author, MultipartFile file);

	void deleteAuthor(Integer id);
}
