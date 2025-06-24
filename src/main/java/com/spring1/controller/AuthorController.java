package com.spring1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring1.model.Author;
import com.spring1.service.AuthorService;

@Controller
@RequestMapping("/author")
public class AuthorController {
	
	@Autowired
	private AuthorService authorService;
	
	//Hien ds tac gia
	@GetMapping("/list")
	public String listAuthor(Model model) {
		model.addAttribute("authors", authorService.getAllAuthors());
		return "authorList";
	}
	
	@GetMapping("/create")
	public String createAuthor(Model model) {
		model.addAttribute("author", new Author());
		return "authorForm";
	}
	
	//luu tac gia
    @PostMapping("/save")
    public String saveAuthor(@ModelAttribute Author author,
                             @RequestParam("file") MultipartFile file,
                             RedirectAttributes redirectAttributes) {
        try {
            authorService.createAuthor(author, file);
            redirectAttributes.addFlashAttribute("message", "Tạo tác giả thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Có lỗi: " + e.getMessage());
        }
        return "redirect:/author/list";
    }
 // Form sửa tác giả
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("author", authorService.getAuthorById(id));
        return "authorForm";
    }

    // Cập nhật tác giả
    @PostMapping("/update/{id}")
    public String updateAuthor(@PathVariable("id") Integer id,
                               @ModelAttribute Author author,
                               @RequestParam("file") MultipartFile file,
                               RedirectAttributes redirectAttributes) {
        try {
            authorService.updateAuthor(id, author, file);
            redirectAttributes.addFlashAttribute("message", "Cập nhật tác giả thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Có lỗi: " + e.getMessage());
        }
        return "redirect:/author/list";
    }

    // Xóa tác giả
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            authorService.deleteAuthor(id);
            redirectAttributes.addFlashAttribute("message", "Xóa tác giả thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Có lỗi: " + e.getMessage());
        }
        return "redirect:/author/list";
    }
}

