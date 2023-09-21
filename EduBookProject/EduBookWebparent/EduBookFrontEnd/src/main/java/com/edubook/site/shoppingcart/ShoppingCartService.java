package com.edubook.site.shoppingcart;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edubook.common.entity.Account;
import com.edubook.common.entity.Book;
import com.edubook.common.entity.ShoppingCart;
import com.edubook.site.book.BookRepository;

@Service
@Transactional
public class ShoppingCartService {

	@Autowired
	private CartRepository repo;
	
	@Autowired
	private BookRepository bookRepo;
	
	public Integer addBook(Integer IDsach, Integer quantity, Account account) {
		Integer updatedQuantity = quantity;
		Book book = new Book(IDsach);
		ShoppingCart shoppingCart = repo.findByAccountAndBook(account, book);
		
		if(shoppingCart == null) {
			shoppingCart = new ShoppingCart();
			shoppingCart.setAccount(account);
			shoppingCart.setBook(book);
		}else {
			updatedQuantity = shoppingCart.getQuantity() + quantity;
		}
		
		shoppingCart.setQuantity(updatedQuantity);
		repo.save(shoppingCart);
		return updatedQuantity;
	}
	
	public List<ShoppingCart> listCartItems(Account account){
		return repo.findByAccount(account);
	}
	
	public int updateQuantity(Integer IDsach, Integer quantity, Account account) {
		repo.updateQuantity(quantity, account.getIDtaikhoan(), IDsach);
		Book book = bookRepo.findById(IDsach).get();
		int subTotal = book.getDongia()*quantity;
		return subTotal;
	}
	
	public void removeBook(Integer IDsach, Account account) {
		repo.deleteByAccountAndBook(account.getIDtaikhoan(), IDsach);
	}
	
	public void deleteByCustomer(Account account) {
		repo.deleteByAccount(account.getIDtaikhoan());
	}
	
}
