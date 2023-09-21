package com.edubook.admin.book;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.edubook.common.entity.Book;

@Service
public class BookService {
	public static final int BOOK_PER_PAGE = 5;
	public static final int REVIEW_PER_PAGE = 5;

	@Autowired 
	private BookRepository repo;
	
	public List<Book> listAll(){
		Sort sort = Sort.by(Sort.Direction.ASC, "IDsach");
		return (List<Book>) repo.findAll(sort);
	}
	
	public Page<Book> listByPage(int pageNum, String keyword){
		Pageable pageable = PageRequest.of(pageNum - 1, BOOK_PER_PAGE);
		if(keyword != null) {
			return repo.findAll(keyword, pageable);
		}
		return repo.findAll(pageable);
	}

	public Book save(Book book) {
		//repo.updateReviewCountAndAverageRating(book.getIDsach());
	    return repo.save(book);
	} 

	public Book get(Integer iDsach) throws BookNotFoundException {
		try {
			return repo.findById(iDsach).get();
		}
		catch(NoSuchElementException ex){
			throw new BookNotFoundException("Không tìm thấy sách nào với ID " + iDsach);
		}
	}

	public void delete(Integer IDsach) throws BookNotFoundException {
		Long countById = repo.countByIDsach(IDsach);
		if(countById == null || countById == 0) {
			throw new BookNotFoundException("Không tìm thấy sách có ID " + IDsach);
		}
		repo.deleteById(IDsach);
	}

	public Book update(Book bookInForm) {
		Book bookInDB = repo.findById(bookInForm.getIDsach()).get();
		if (bookInForm.getHinhanh() != null) {
			bookInDB.setHinhanh(bookInForm.getHinhanh());
		}
		bookInDB.setIDsach(bookInForm.getIDsach());
		bookInDB.setTensach(bookInForm.getTensach());
		bookInDB.setTacgia(bookInForm.getTacgia());
		bookInDB.setNamxuatban(bookInForm.getNamxuatban());
		bookInDB.setNxb(bookInForm.getNxb());
		bookInDB.setMota(bookInForm.getMota());
		bookInDB.setSoluongkho(bookInForm.getSoluongkho());
		bookInDB.setDongia(bookInForm.getDongia());
		bookInDB.setTheloai(bookInForm.getTheloai());
		bookInDB.setHinhanh(bookInForm.getHinhanh());
		
		return repo.save(bookInDB);
	}
	
	public boolean isTensachUnique(String tensach) {
		Book bookByTensach = repo.getBookByTensach(tensach);
		return bookByTensach == null;
	}
}
