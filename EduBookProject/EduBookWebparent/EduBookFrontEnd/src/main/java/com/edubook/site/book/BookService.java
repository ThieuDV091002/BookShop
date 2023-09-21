package com.edubook.site.book;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import com.edubook.common.entity.Book;

@Service
public class BookService {
	
	public static final int SEARCH_RESULTS_PER_PAGE = 6;
	
	@Autowired
	private BookRepository repo;

	public Page<Book> search(String keyword, int pageNum){
		Pageable pageable = PageRequest.of(pageNum - 1, SEARCH_RESULTS_PER_PAGE);
		return repo.search(keyword, pageable);
	}

	public Book get(Integer iDsach) throws BookNotFoundException {
		try {
			return repo.findById(iDsach).get();
		}catch(NoSuchElementException ex) {
			throw new BookNotFoundException("Không tìm thấy sách với ID: " + iDsach);
		}
	}
	
}
