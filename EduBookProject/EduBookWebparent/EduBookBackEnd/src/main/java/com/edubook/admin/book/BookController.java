package com.edubook.admin.book;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edubook.admin.FileUploadUtil;
import com.edubook.common.entity.Book;
import com.edubook.common.entity.Review;

@Controller
public class BookController {

    @Autowired 
    private BookService bookService;
    
    @GetMapping("/book")
    public String listFirstBookPage(Model model) {
        return listByPage(1, model, null);
    }
    
    @GetMapping("/book-page-{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model, @Param("keyword") String keyword) {
    	Page<Book> page = bookService.listByPage(pageNum, keyword);
    	List<Book> listBooks = page.getContent();
    	
    	long startCount = (pageNum -1)*BookService.BOOK_PER_PAGE + 1;
    	long endCount = startCount + BookService.BOOK_PER_PAGE - 1;
    	if(endCount > page.getTotalElements()) {
    		endCount = page.getTotalElements();
    	}
    	
    	model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
    	model.addAttribute("totalItems", page.getTotalElements());
    	model.addAttribute("startCount", startCount);
    	model.addAttribute("endCount", endCount);
    	model.addAttribute("listBooks", listBooks);
    	model.addAttribute("keyword", keyword);
    	return "books/book";
    }
    
    @GetMapping("/book-new")
    public String newBook(Model model) {
    	Book book = new Book();
		model.addAttribute("book", book);
		model.addAttribute("pageTitle", "Thêm sách");
    	return "books/book_form";
    }
    
    @PostMapping("/book-save")
    public String saveBook(Book book, RedirectAttributes redirectAttributes,
    		@RequestParam("image") MultipartFile multipartFile) throws IOException {
    	if(!multipartFile.isEmpty()) {
    		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
    		book.setHinhanh(fileName);
    		Book savedBook = bookService.update(book);
        	String uploadDir = "../book-photos/" + savedBook.getIDsach();
        	FileUploadUtil.cleanDir(uploadDir);
        	FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
    	}
    	else {
    		if(book.getHinhanh().isEmpty()) book.setHinhanh(null);
    		bookService.update(book);
    	}
		redirectAttributes.addFlashAttribute("message", book.getTensach() + " đã được thêm thành công");
		return "redirect:/book";
	}
    
    @PostMapping("/book-update")
    public String updateBook(Book book, RedirectAttributes redirectAttributes,
    		@RequestParam("image") MultipartFile multipartFile) throws IOException {
    	if(!multipartFile.isEmpty()) {
    		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
    		book.setHinhanh(fileName);
    		Book savedBook = bookService.save(book);
        	String uploadDir = "../book-photos/" + savedBook.getIDsach();
        	FileUploadUtil.cleanDir(uploadDir);
        	FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
    	}
    	else {
    		if(book.getHinhanh().isEmpty()) book.setHinhanh(null);
    		bookService.save(book);
    	}
		redirectAttributes.addFlashAttribute("message", book.getTensach() + " đã được lưu thành công");
		return "redirect:/book";
	}
    

	@GetMapping("/book-edit-{IDsach}")
    public String editBook(@PathVariable(name = "IDsach") Integer IDsach, RedirectAttributes redirectAttributes, Model model) {
    	try {
    		Book book = bookService.get(IDsach);
    		model.addAttribute("book", book);
    		model.addAttribute("pageTitle", "Sửa thông tin sách(ID :" + IDsach + ")");
    		return "books/book_update";
    	}catch(BookNotFoundException ex) {
    		redirectAttributes.addFlashAttribute("message", ex.getMessage());
    		return "redirect:/book";
    	}
	}
	
	@GetMapping("/book-detail-{IDsach}")
    public String viewBookDetail(@PathVariable(name = "IDsach") Integer IDsach, RedirectAttributes redirectAttributes, Model model) {
    	try {
    		Book book = bookService.get(IDsach);
    		model.addAttribute("book", book);
    		return "books/book_detail";
    	}catch(BookNotFoundException ex) {
    		redirectAttributes.addFlashAttribute("message", ex.getMessage());
    		return "redirect:/book";
    	}
	}
    
    @GetMapping("/book-delete-{IDsach}")
    public String deleteBook(@PathVariable(name = "IDsach") Integer IDsach, RedirectAttributes redirectAttributes, Model model) {
    	try {
    		bookService.delete(IDsach);
    		redirectAttributes.addFlashAttribute("message", "Sách có ID: " + IDsach + " đã được xoá thành công");
    	}catch(BookNotFoundException ex) {
    		redirectAttributes.addFlashAttribute("message", ex.getMessage());
    	}
    	return "redirect:/book";
    }
    
}

