package com.edubook.admin.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edubook.common.entity.Review;

@Service
public class ReviewService {

	public static final int REVIEW_PER_PAGE=5;
	
	@Autowired
	private ReviewRepository repo;
	
	public Page<Review> listByPage(int pageNum, String keyword){
		Pageable pageable = PageRequest.of(pageNum - 1, REVIEW_PER_PAGE);
		if(keyword != null) {
			return repo.findAll(keyword, pageable);
		}
		return repo.findAll(pageable);
	}
	
}
