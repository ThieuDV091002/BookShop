package com.edubook.site.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edubook.common.entity.Book;
import com.edubook.common.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>{

	@Query("SELECT r FROM Review r WHERE r.account.IDtaikhoan = ?1")
	public Page<Review> findByCustomer(Integer IDtaikhoan, Pageable pageable);
	
	@Query("SELECT r FROM Review r WHERE r.account.IDtaikhoan = ?1 AND ("
			+ "r.comment LIKE %?2% OR "
			+ "r.book.tensach LIKE %?2%)")
	public Page<Review> findByCustomer(Integer IDtaikhoan, String keyword, Pageable pageable);
	
	@Query("SELECT r FROM Review r WHERE r.account.IDtaikhoan = ?1 AND r.IDreview = ?2")
	public Review findByCustomerAndIDreview(Integer IDtaikhoan, Integer IDreview);
	
	public Page<Review> findByBook(Book book, Pageable pageable);
	
	@Query("SELECT COUNT(r.IDreview) FROM Review r WHERE r.account.IDtaikhoan = ?1 AND "
			+ "r.book.IDsach = ?2")
	public Long countByCustomerAndBook(Integer IDtaikhoan, Integer IDsach);
	
	public Long countByIDreview(Integer IDreview);
	
	public void deleteById(Integer IDreview);
	
	@Query("SELECT r FROM Review r WHERE r.IDreview = :IDreview")
	public Review getReviewByIDreview(@Param("IDreview") Integer IDreview);

}
