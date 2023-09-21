package com.edubook.site.review;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.edubook.common.entity.Account;
import com.edubook.common.entity.Book;
import com.edubook.common.entity.OrderStatus;
import com.edubook.common.entity.Review;
import com.edubook.site.book.BookRepository;
import com.edubook.site.order.OrderDetailRepository;

@Service
@Transactional
public class ReviewService {
	public static final int REVIEW_PER_PAGE = 5;

	@Autowired
	private ReviewRepository repo;
	@Autowired
	private OrderDetailRepository orderDetailRepo;
	@Autowired
	private BookRepository bookRepo;
	
	public Page<Review> listByPage(int pageNum, String keyword, Account account){
		Pageable pageable = PageRequest.of(pageNum - 1, REVIEW_PER_PAGE);
		if(keyword != null) {
			return repo.findByCustomer(account.getIDtaikhoan(),keyword, pageable);
		}
		return repo.findByCustomer(account.getIDtaikhoan(), pageable);
	}
	
	public Review getByCustomerAndId(Account account, Integer IDreview) throws ReviewNotFoundException {
		Review review = repo.findByCustomerAndIDreview(account.getIDtaikhoan(), IDreview);
		if (review == null) 
			throw new ReviewNotFoundException("Bạn không có đánh giá nào với ID " + IDreview);
		
		return review;
	}
	
	public Page<Review> list3MostRecentReviewsByBook(Book book){
		Sort sort = Sort.by("reviewTime").descending();
		Pageable pageable = PageRequest.of(0, 3, sort);
		return repo.findByBook(book, pageable);
	}
	
	public Page<Review> listByBook(Book book, int pageNum, String sortField, String sortDir){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, REVIEW_PER_PAGE, sort);
		return repo.findByBook(book, pageable);
	}
	
	public boolean didCustomerReviewBook(Account account, Integer IDsach) {
		Long count = repo.countByCustomerAndBook(account.getIDtaikhoan(), IDsach);
		return count > 0;
	}
	
	public boolean canCustomerReviewBook(Account account, Integer IDsach) {
		Long count = orderDetailRepo.countByBookAndAccountAndOrderStatus(IDsach, account.getIDtaikhoan(), OrderStatus.DELIVERED);
		return count>0;
	}
	
	public Review save(Review review) {
		review.setReviewTime(new Date());
		Review savedReview = repo.save(review);
		
		Integer IDsach = savedReview.getBook().getIDsach();		
		bookRepo.updateReviewCountAndAverageRating(IDsach);
		
		return savedReview;
	}
	
	public void delete(Integer IDreview) throws ReviewNotFoundException {
		Long countById = repo.countByIDreview(IDreview);
		Review deleteReview = repo.getReviewByIDreview(IDreview);
		if(countById == null || countById == 0) {
			throw new ReviewNotFoundException("Không tìm thấy đánh giá có ID " + IDreview);
		}
		repo.deleteById(IDreview);
		Integer IDsach = deleteReview.getBook().getIDsach();
		bookRepo.updateReviewCountAndAverageRating(IDsach);
	}
	
}
