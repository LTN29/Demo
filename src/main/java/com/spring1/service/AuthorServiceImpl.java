package com.spring1.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring1.model.Author;
import com.spring1.repository.AuthorRepository;
@Service
public class AuthorServiceImpl implements AuthorService {

	@Autowired
	private AuthorRepository authorRepo;
	
	private static final String UPLOAD_DIR="uploads";
	
	@Override
	public List<Author> getAllAuthors() {
		// TODO Auto-generated method stub
		return authorRepo.findAll();
	}

	@Override
	public Author getAuthorById(Integer id) {
		// TODO Auto-generated method stub
		return authorRepo.findById(id).orElseThrow(()-> new RuntimeException("khong tim thay tac gia co id:" +id));
	}
	
	//ham xu ly upload file(chung cho create/upadate)
	private void handleFileUpload(Author author, MultipartFile file) {
		try {
			if (file != null && !file.isEmpty()) {
				File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) uploadDir.mkdirs();
                String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                file.transferTo(new File(uploadDir, filename));
                author.setImage(filename);
            } else if (author.getImage() == null || author.getImage().isEmpty()) {
                author.setImage("noimage.png");
            }
		}catch(IOException e) {
			throw new RuntimeException("upload fail",e);
		}
	}
	
	@Override
	public Author createAuthor(Author author, MultipartFile file) {
		handleFileUpload(author ,file);
		if (author.getInformation() ==null || author.getInformation().isEmpty()) {
			author.setInformation("no_content");
		}
		return authorRepo.save(author);
	}

	@Override
	public Author updateAuthor(Integer id, Author author, MultipartFile file) {
		Author existing = getAuthorById(id);
		existing.setName(author.getName());
		existing.setInformation(author.getInformation());
		handleFileUpload(existing, file);
		return authorRepo.save(existing);
	}

	@Override
	public void deleteAuthor(Integer id) {
		authorRepo.deleteById(id);
		
	}
	
	

}
