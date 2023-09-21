package com.edubook.site.review;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edubook.common.entity.Account;
import com.edubook.common.entity.Book;
import com.edubook.common.entity.Review;
import com.edubook.site.FileUploadUtil;
import com.edubook.site.book.BookNotFoundException;
import com.edubook.site.book.BookService;
import com.edubook.site.customer.CustomerService;

@Controller
public class ReviewController {

	@Autowired
	private ReviewService service;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private BookService bookService;
	
	@GetMapping("/review")
	public String listFirstPage(Model model, HttpServletRequest request) {
		return listReviewsByCustomerByPage(model, request, 1, null);
	}
	
	@GetMapping("/review-page-{pageNum}") 
	public String listReviewsByCustomerByPage(Model model, HttpServletRequest request,
							@PathVariable(name = "pageNum") int pageNum, String keyword) {
		Account account = getAuthenticatedAccount(request);
		Page<Review> page = service.listByPage(pageNum, keyword, account);		
		List<Review> listReviews = page.getContent();
		
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("keyword", keyword);
		
		model.addAttribute("listReviews", listReviews);

		long startCount = (pageNum - 1) * ReviewService.REVIEW_PER_PAGE + 1;
		model.addAttribute("startCount", startCount);
		
		long endCount = startCount + ReviewService.REVIEW_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("endCount", endCount);
		
		return "review/review_customer";
	}

	@GetMapping("/review-detail-{IDreview}")
	public String viewReviewDetails(Model model,
			@PathVariable(name = "IDreview") Integer IDreview, HttpServletRequest request) throws ReviewNotFoundException {
		Account account = getAuthenticatedAccount(request);
		Review review = service.getByCustomerAndId(account, IDreview);
		
		model.addAttribute("review", review);
		model.addAttribute("account", account);
		
		return "review/review_detail";
	}
	
	@GetMapping("/write_review-book-{IDsach}")
	public String showReviewForm(Model model, @PathVariable(name = "IDsach") Integer IDsach, 
			HttpServletRequest request) throws BookNotFoundException {
		Review review = new Review();
		Book book = bookService.get(IDsach);
		Account account = getAuthenticatedAccount(request);
		boolean customerReviewed = service.didCustomerReviewBook(account, book.getIDsach());
		
		if (customerReviewed) {
			model.addAttribute("customerReviewed", customerReviewed);
		} else {
			boolean customerCanReview = service.canCustomerReviewBook(account, book.getIDsach());
			
			if (customerCanReview) {
				model.addAttribute("customerCanReview", customerCanReview);				
			} else {
				model.addAttribute("NoReviewPermission", true);
			}
		}		
		
		model.addAttribute("book", book);
		model.addAttribute("review", review);
		
		return "review/review_form";
	}
	
	@PostMapping("/review-save")
	public String saveReview(Model model, Review review, Integer IDsach, 
			HttpServletRequest request, @RequestParam("image") MultipartFile multipartFile) throws IOException, BookNotFoundException {
		Account account = getAuthenticatedAccount(request);
		Book book = bookService.get(IDsach);
		review.setBook(book);
		review.setAccount(account);
		
		if(!multipartFile.isEmpty()) {
    		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
    		review.setPhoto(fileName);
    		Review savedReview = service.save(review);
    		model.addAttribute("review", savedReview);
        	String uploadDir = "../review-photos/" + savedReview.getIDreview();
        	FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
    	}
    	else {
    		if(review.getPhoto().isEmpty()) review.setPhoto(null);
    		service.save(review);
    		model.addAttribute("review", review);
    	}
		
		return "review/review_done";
	}
	
	@GetMapping("/rating-{IDsach}-page-{pageNum}") 
	public String listByBookByPage(Model model,
				@PathVariable(name = "IDsach") Integer IDsach,
				@PathVariable(name = "pageNum") int pageNum,
				String sortField, String sortDir,
				HttpServletRequest request) throws BookNotFoundException {
		
		Book book = bookService.get(IDsach);
		
		Page<Review> page = service.listByBook(book, pageNum, sortField, sortDir);
		List<Review> listReviews = page.getContent();
		
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listReviews", listReviews);
		model.addAttribute("book", book);

		long startCount = (pageNum - 1) * ReviewService.REVIEW_PER_PAGE + 1;
		model.addAttribute("startCount", startCount);
		
		long endCount = startCount + ReviewService.REVIEW_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("endCount", endCount);
		
		return "review/review_book";
	}
	
	@GetMapping("/rating-{IDsach}")
	public String listByBookFirstPage(@PathVariable(name = "IDsach") Integer IDsach, Model model,
			HttpServletRequest request) throws BookNotFoundException {
		return listByBookByPage(model, IDsach, 1, "reviewTime", "desc", request);
	}	
	
	@GetMapping("/review-delete-{IDreview}")
	public String delete(@PathVariable(name = "IDreview") Integer IDreview, RedirectAttributes redirectAttributes, Model model) {
		try {
    		service.delete(IDreview);
    	}catch(ReviewNotFoundException ex) {
    		redirectAttributes.addFlashAttribute("message", ex.getMessage());
    	}
		return "redirect:/review";
	}
	
	private Account getAuthenticatedAccount(HttpServletRequest request) {
		String customerName = getUsernameOfAuthenticatedCustomer(request);
		Account account = customerService.getAccountByUsername(customerName);
		String customerEmail = account.getEmail();
		return customerService.getAccountByEmail(customerEmail);
	}
	
	public static String getUsernameOfAuthenticatedCustomer(HttpServletRequest request) {
	    Object principal = request.getUserPrincipal();
	    String customerName = null;
	    if (principal instanceof UsernamePasswordAuthenticationToken) {
	        customerName = request.getUserPrincipal().getName();
	    }
	    return customerName;
	}
}
